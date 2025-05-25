import React, { useState, useEffect } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import api from "../api/api";
import "../styles/LessonDetailPage.css";

export default function LessonDetailPage() {
  const { lessonId } = useParams();
  const [lessonInfo, setLessonInfo] = useState(null);
  const [lessonTerms, setLessonTerms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  // Dohvati podatke o lekciji
  useEffect(() => {
    api.get(`/lessons/${lessonId}`)
      .then(res => setLessonInfo(res.data))
      .catch(() => setLessonInfo(null));
  }, [lessonId]);

  // Dohvati termine
  useEffect(() => {
    api.get(`/terms/lesson/${lessonId}`)
      .then(res => {
        setLessonTerms(res.data);
        setLoading(false);
      })
      .catch(err => {
        setError("Greška pri dohvaćanju termina!");
        setLoading(false);
      });
  }, [lessonId]);

  if (loading) return <div className="loading">Učitavam termine...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="lesson-detail-container">
      <Link to="/lekcije" className="back-btn">&larr; Povratak na lekcije</Link>
      
      {/* Pregled lekcije */}
      {lessonInfo && (
        <div className="lesson-info-overview">
          <h2>
            {lessonInfo.lessonNameCro}
            <span className="lesson-jpn-title"> {lessonInfo.lessonNameJpn}</span>
          </h2>
          <div className="lesson-desc">
            <p>{lessonInfo.descriptionCro}</p>
            <p className="lesson-desc-jpn">{lessonInfo.descriptionJpn}</p>
          </div>
          <div className={`difficulty-badge diff-${lessonInfo.difficulty}`}>
          Težina: {lessonInfo.difficulty}
          </div>
        </div>
      )}

      <h2 className="lesson-overview-title">Pregled lekcije</h2>
      <div className="terms-grid">
        {lessonTerms
          .slice()
          .sort((a, b) => a.termNum - b.termNum)
          .map(lessonTerm => (
            <TermItem key={lessonTerm.term.id} lessonTerm={lessonTerm} />
        ))}
      </div>

      <LessonTasks lessonId={lessonId} />
    </div>
  );
}

function TermItem({ lessonTerm }) {
  return (
    <div className="term-card">
      <div className="term-header">
        <h3>{lessonTerm.term.termNameCro}</h3>
        <span className="term-jpn">{lessonTerm.term.termNameJpn}</span>
      </div>
      <div className="term-content">
        <p className="term-description">{lessonTerm.term.descriptionCro}</p>
        <p className="term-description-jpn">{lessonTerm.term.descriptionJpn}</p>
        <div className="term-meta">
          <span className={`difficulty diff-${lessonTerm.term.difficulty}`}>
            Težina: {lessonTerm.term.difficulty}
          </span>
        </div>
      </div>
    </div>
  );
}

// NOVA KOMPONENTA ZA TESTOVE
function LessonTasks({ lessonId }) {
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    api.get(`/tasks/lesson/all/${lessonId}`)
      .then(res => {
        setTasks(res.data);
        setLoading(false);
      })
      .catch(() => {
        setError("Greška pri dohvaćanju testova!");
        setLoading(false);
      });
  }, [lessonId]);

  if (loading) return <div className="loading">Učitavam testove...</div>;
  if (error) return <div className="error">{error}</div>;
  if (tasks.length === 0) return <div className="info-msg">Nema dostupnih testova za ovu lekciju.</div>;

  return (
    <div className="lesson-tasks-section">
      <h2 className="lesson-tasks-title">Testovi</h2>
      <div className="lesson-tasks-list">
        {tasks.map(task => (
          <div
            key={task.id}
            className="lesson-task-card"
            onClick={() => navigate(`/lekcije/${lessonId}/test?taskId=${task.id}`)}
            tabIndex={0}
            role="button"
          >
            <div className="lesson-task-header">
              <span className="lesson-task-title">{task.taskNameCro}</span>
              <span className="lesson-task-jpn">{task.taskNameJpn}</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
