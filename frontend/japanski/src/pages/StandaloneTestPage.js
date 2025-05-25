import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/api";
import "../styles/LessonTestPage.css";

export default function StandaloneTestPage() {
  const navigate = useNavigate();
  const [tasks, setTasks] = useState([]);
  const [selectedTask, setSelectedTask] = useState(null);
  const [questions, setQuestions] = useState([]);
  const [loading, setLoading] = useState({ tasks: true, questions: true });
  const [error, setError] = useState("");
  const [answers, setAnswers] = useState({});
  const [showHints, setShowHints] = useState({});
  const [results, setResults] = useState({});
  const [submitted, setSubmitted] = useState(false);

  // Dohvati sve zadatke i odaberi nasumično
  useEffect(() => {
    api.get("/tasks/standalone/all")
      .then(res => {
        setTasks(res.data);
        const randomTask = res.data[Math.floor(Math.random() * res.data.length)];
        setSelectedTask(randomTask);
        setLoading(prev => ({ ...prev, tasks: false }));
      })
      .catch(err => {
        setError("Greška pri dohvaćanju zadataka!");
        setLoading(prev => ({ ...prev, tasks: false }));
      });
  }, []);

  // Dohvati pitanja za odabrani zadatak
  useEffect(() => {
    if (selectedTask) {
      api.get(`/questions/standalone/all/${selectedTask.id}`)
        .then(res => {
          const randomized = res.data.map(q => ({
            ...q,
            lang: Math.random() < 0.5 ? "HR" : "JP"
          }));
          setQuestions(randomized);
          setLoading(prev => ({ ...prev, questions: false }));
          
          const initialAnswers = {};
          const initialHints = {};
          randomized.forEach(q => {
            initialAnswers[q.id] = "";
            initialHints[q.id] = false;
          });
          setAnswers(initialAnswers);
          setShowHints(initialHints);
        })
        .catch(err => {
          setError("Greška pri dohvaćanju pitanja!");
          setLoading(prev => ({ ...prev, questions: false }));
        });
    }
  }, [selectedTask]);

  const handleAnswerChange = (questionId, value) => {
    setAnswers(prev => ({ ...prev, [questionId]: value }));
  };

  const handleHintClick = (questionId) => {
    setShowHints(prev => ({ ...prev, [questionId]: true }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
    const newResults = {};
    questions.forEach(question => {
      // Ako je pitanje na HR, očekuje se japanski odgovor, i obrnuto
      const correctAnswer = question.lang === "HR"
        ? (question.term.termNameJpn || "").trim().toLowerCase()
        : (question.term.termNameCro || "").trim().toLowerCase();
      
      const userAnswer = (answers[question.id] || "").trim().toLowerCase();
      newResults[question.id] = userAnswer === correctAnswer;
    });

    setResults(newResults);
    setSubmitted(true);
  };

  if (loading.tasks || loading.questions) return <div className="loading">Pripremam test...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="test-container">
      <Link to="/lekcije" className="back-btn">&larr; Povratak na lekcije</Link>
      
      {selectedTask && (
        <div className="test-header">
          <h2>
            {selectedTask.taskNameCro}
            <span className="jpn-text"> {selectedTask.taskNameJpn}</span>
          </h2>
          <div className="task-difficulty">
            Težina testa: {selectedTask.difficulty}
          </div>
        </div>
      )}

      <form onSubmit={handleSubmit} className="test-form">
        {questions.map((question, index) => (
          <div key={question.id} className="question-item">
            <div className="question-header">
              <h3>Pitanje {index + 1}</h3>
            </div>
            
            <div className="question-text">
              {question.lang === "HR" ? (
                <p>{question.questionCro}</p>
              ) : (
                <p className="jpn-text">{question.questionJpn}</p>
              )}
            </div>

            <input
              type="text"
              value={answers[question.id]}
              onChange={(e) => handleAnswerChange(question.id, e.target.value)}
              placeholder="Upiši svoj odgovor..."
              className="answer-input"
              disabled={submitted}
            />

            {submitted && (
              <div className="result-indicator">
                {results[question.id] ? (
                  <span className="correct">✓ Točno!</span>
                ) : (
                  <>
                    <span className="incorrect">✗ Netočno</span>
                    <div className="correct-answer">
                      Točan odgovor:{" "}
                      {question.lang === "HR"
                        ? question.term.termNameJpn
                        : question.term.termNameCro}
                    </div>
                  </>
                )}
              </div>
            )}

            {!showHints[question.id] ? (
              <button
                type="button"
                className="hint-btn"
                onClick={() => handleHintClick(question.id)}
                disabled={submitted}
              >
                Prikaži hint
              </button>
            ) : (
              <div className="question-hints">
                {question.lang === "HR"
                  ? <span>Hint: {question.hintCro}</span>
                  : <span className="jpn-text">ヒント: {question.hintJpn}</span>
                }
              </div>
            )}
          </div>
        ))}
        <button 
        type="submit" 
        className="submit-test-btn"
        disabled={submitted}
        onClick={submitted ? () => navigate("/lekcije") : undefined}
      >
        {submitted ? "Test je završen" : "Provjeri odgovore"}
      </button>
      </form>
    </div>
  );
}
