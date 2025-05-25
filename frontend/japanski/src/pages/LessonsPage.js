import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../api/api";
import "../styles/LessonsPage.css";

export default function LessonsPage() {
  const [activeTab, setActiveTab] = useState("lessons");
  const [lessons, setLessons] = useState([]);
  const [terms, setTerms] = useState([]);
  const [loading, setLoading] = useState({ lessons: true, terms: true });
  const [error, setError] = useState("");
  const [searchTerm, setSearchTerm] = useState(""); // za filtriranje rječnika

  // Dohvati lekcije
  useEffect(() => {
    api.get("/lessons")
      .then(res => {
        setLessons(res.data);
        setLoading(prev => ({ ...prev, lessons: false }));
      })
      .catch(() => setError("Greška pri dohvaćanju lekcija!"));
  }, []);

  // Dohvati termine za rječnik
  useEffect(() => {
    if (activeTab === "dictionary") {
      api.get("/terms")
        .then(res => {
          setTerms(res.data);
          setLoading(prev => ({ ...prev, terms: false }));
        })
        .catch(() => setError("Greška pri dohvaćanju rječnika!"));
    }
  }, [activeTab]);

  // Filtriraj i sortiraj termine
  const filteredTerms = terms
    .filter(term =>
      term.termNameCro.toLowerCase().includes(searchTerm.toLowerCase()) ||
      term.termNameJpn.toLowerCase().includes(searchTerm.toLowerCase())
    )
    .sort((a, b) => a.termNameCro.localeCompare(b.termNameCro, "hr", { sensitivity: "base" }));

  const renderContent = () => {
    switch (activeTab) {
      case "lessons":
        return (
          <div className="lessons-list">
            {lessons.map(lesson => (
              <div className="lesson-card" key={lesson.id}>
                <div className="lesson-header">
                  <div>
                    <h2 className="lesson-title">{lesson.lessonNameCro}</h2>
                    <div className="jpn-text lesson-title-jpn">{lesson.lessonNameJpn}</div>
                  </div>
                  <div className={`difficulty-badge diff-${lesson.difficulty}`}>
                    Težina: {lesson.difficulty}
                  </div>
                </div>
                <div className="lesson-desc">
                  <p>{lesson.descriptionCro}</p>
                  <p className="jpn-text">{lesson.descriptionJpn}</p>
                  <Link to={`/lekcije/${lesson.id}`} className="lesson-start-btn">
                    Započni lekciju
                  </Link>
                </div>
              </div>
            ))}
          </div>
        );

      case "dictionary":
        return (
          <div>
            <div className="dictionary-search-wrap">
              <input
                type="text"
                className="dictionary-search"
                placeholder="Pretraži riječ (hrvatski ili japanski)..."
                value={searchTerm}
                onChange={e => setSearchTerm(e.target.value)}
              />
            </div>
            <div className="dictionary-list">
              {filteredTerms.map(term => (
                <div className="term-card line" key={term.id}>
                  <div className="term-header">
                    <h3>{term.termNameCro}</h3>
                    <span className="jpn-text">{term.termNameJpn}</span>
                  </div>
                  <div className="term-desc">
                    <p>{term.descriptionCro}</p>
                    <p className="jpn-text">{term.descriptionJpn}</p>

                    {/* Prikaz slike ako postoji */}
                    {term.imageFile && (
                      <div className="term-image">
                        <img
                          src={`http://localhost:8080/files/${term.imageFile}`}
                          alt={`Ilustracija za pojam ${term.termNameCro}`}
                          style={{ 
                            maxWidth: "200px",
                            marginTop: "12px",
                            borderRadius: "8px",
                            boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
                          }}
                        />
                      </div>
                    )}

                    {/* Prikaz audio playera ako postoji */}
                    {term.audioUuid && (
                      <div className="term-audio" style={{ marginTop: "12px" }}>
                        <audio controls controlsList="nodownload">
                          <source 
                            src={`http://localhost:8080/files/${term.audioUuid}`} 
                            type="audio/mpeg" 
                          />
                          Vaš preglednik ne podržava audio reprodukciju
                        </audio>
                      </div>
                    )}
                  </div>
                  <div className="term-meta">
                    <span className={`difficulty-badge diff-${term.difficulty}`}>
                      Težina: {term.difficulty}
                    </span>
                  </div>
                </div>
              ))}
              {filteredTerms.length === 0 && (
                <div className="no-results">Nema rezultata za traženi pojam.</div>
              )}
            </div>
          </div>
        );

      default:
        return null;
    }
  };

  return (
    <div className="lessons-container">
      <div className="navigation-tabs">
        <button 
          className={`tab-btn ${activeTab === "lessons" ? "active" : ""}`}
          onClick={() => setActiveTab("lessons")}
        >
          Moje lekcije
        </button>
        <button
          className={`tab-btn ${activeTab === "dictionary" ? "active" : ""}`}
          onClick={() => setActiveTab("dictionary")}
        >
          Rječnik
        </button>
        <Link to="/test" className={`tab-btn test-tab${activeTab === "test" ? " active" : ""}`}>
          Testiraj znanje
        </Link>
      </div>

      {loading.lessons && activeTab === "lessons" && <div className="loading">Učitavam lekcije...</div>}
      {loading.terms && activeTab === "dictionary" && <div className="loading">Učitavam rječnik...</div>}
      {error && <div className="error">{error}</div>}

      {renderContent()}
    </div>
  );
}
