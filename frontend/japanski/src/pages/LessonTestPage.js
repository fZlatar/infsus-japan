import React, { useState, useEffect } from 'react';
import { useParams, Link, useSearchParams } from 'react-router-dom';
import api from '../api/api';
import '../styles/LessonTestPage.css';

export default function LessonTestPage() {
  const { lessonId } = useParams();
  const [searchParams] = useSearchParams();
  const taskId = searchParams.get('taskId');
  const email = localStorage.getItem('userEmail');

  const [processTask, setProcessTask] = useState(null);

  const [questions, setQuestions] = useState([]);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [answers, setAnswers] = useState({});
  const [showHints, setShowHints] = useState({});
  const [results, setResults] = useState({});
  const [submitted, setSubmitted] = useState(false);
  const [startProcess, setStartProcess] = useState(false);
  const [showAnswers, setShowAnswers] = useState(false);

  const handleRetakeTest = () => {
    setSubmitted(false);
    setCurrentQuestionIndex(0);
    setStartProcess(false);
    setProcessTask(null);
    setShowAnswers(false);

    const resetHints = {};

    questions.forEach((q) => {
      resetHints[q.id] = false;
    });

    setShowHints(resetHints);
  };

  const handleStartProcess = async () => {
    api
      .post('/api/camunda/start-test-process', {
        email,
        lessonId,
      })
      .then((res) => {
        const data = res.data;
        console.log(`Started process with ID: ${data}`);
        setStartProcess(true);
      })
      .catch((err) => {
        console.error(err);
      });

    loadTasks();
  };

  const loadTasks = async () => {
    api
      .get(`/api/camunda/tasks?email=${email}`)
      .then((res) => {
        const data = res.data;
        if (data && data.length > 0) {
          setProcessTask(data[0]);
        }
        console.log(data);
      })
      .catch((err) => {
        console.error(err);
      });
  };

  const handleComplete = async (correct) => {
    api
      .post(`/api/camunda/complete-task/${processTask.taskId}`, {
        correctAnswers: correct,
      })
      .then((res) => {
        const data = res.data;
        console.log(data);
        if (data.passed) {
          alert('Uspješno odrađen zadatak');
        } else {
          alert('Zadatak nije uspješno odrađen');
        }
      })
      .catch((err) => {
        console.error(err);
      });
  };

  useEffect(() => {
    if (!taskId) return;

    api
      .get(`/questions/lesson/all/${taskId}`)
      .then((res) => {
        const randomized = res.data.map((q) => ({
          ...q,
          lang: Math.random() < 0.5 ? 'HR' : 'JP',
        }));
        setQuestions(randomized);
        setLoading(false);

        const initialAnswers = {};
        const initialHints = {};
        randomized.forEach((q) => {
          initialAnswers[q.id] = '';
          initialHints[q.id] = false;
        });
        setAnswers(initialAnswers);
        setShowHints(initialHints);
      })
      .catch((err) => {
        setError('Greška pri dohvaćanju pitanja!');
        setLoading(false);
      });
  }, [taskId]);

  useEffect(() => {
    if (!email || !lessonId || !taskId || questions.length === 0) return;

    api
      .get(`/progresses/${email}/${lessonId}`)
      .then((res) => {
        const progress = res.data;
        if (
          progress &&
          progress.termNum &&
          progress.termNum.toString() === taskId.toString()
        ) {
          const qNum = Math.max(
            0,
            Math.min(progress.questionNum, questions.length - 1)
          );
          setCurrentQuestionIndex(qNum);
        }
      })
      .catch(() => {});
  }, [email, lessonId, taskId, questions]);

  const handleAnswerChange = (questionId, value) => {
    setAnswers((prev) => ({ ...prev, [questionId]: value }));
  };

  const handleHintClick = (questionId) => {
    setShowHints((prev) => ({ ...prev, [questionId]: true }));
  };

  const handleNext = () => {
    if (currentQuestionIndex < questions.length - 1) {
      setCurrentQuestionIndex((prev) => prev + 1);
    }
  };

  const handlePrev = () => {
    if (currentQuestionIndex > 0) {
      setCurrentQuestionIndex((prev) => prev - 1);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    console.log(answers);

    const newResults = {};
    questions.forEach((question) => {
      // Ako je pitanje na HR, očekuje se japanski odgovor, i obrnuto
      const correctAnswer =
        question.lang === 'HR'
          ? question.lessonTerm.term.termNameJpn.trim().toLowerCase()
          : question.lessonTerm.term.termNameCro.trim().toLowerCase();

      const userAnswer = (answers[question.id] || '').trim().toLowerCase();

      newResults[question.id] = userAnswer === correctAnswer;
    });

    const correctCount = Object.values(newResults).filter(
      (val) => val === true
    ).length;

    handleComplete(correctCount);

    setResults(newResults);
    setSubmitted(true);
    setShowAnswers(true);

    api
      .post(`/progresses/${email}/${lessonId}`, {
        termNum: Number(taskId),
        questionNum: currentQuestionIndex,
      })
      .catch(() => alert('Greška pri spremanju napretka!'));
  };

  if (!taskId) return <div className="error">Nije odabran test!</div>;
  if (loading) return <div className="loading">Učitavam pitanja...</div>;
  if (error) return <div className="error">{error}</div>;
  if (questions.length === 0)
    return <div className="error">Nema dostupnih pitanja</div>;

  const currentQuestion = questions[currentQuestionIndex];

  return (
    <div className="test-container">
      <Link to={`/lekcije/${lessonId}`} className="back-btn">
        &larr; Povratak na lekciju
      </Link>

      {!startProcess ? (
        <button
          type="button"
          className="start-test-btn"
          onClick={handleStartProcess}
        >
          Započni test
        </button>
      ) : (
        <>
          <h2>
            Testiraj svoje znanje ({currentQuestionIndex + 1}/{questions.length}
            )
          </h2>

          <div className="navigation-controls">
            <button
              type="button"
              onClick={handlePrev}
              disabled={currentQuestionIndex === 0}
            >
              &larr; Prethodno
            </button>

            <button
              type="button"
              onClick={handleNext}
              disabled={currentQuestionIndex === questions.length - 1}
            >
              Sljedeće &rarr;
            </button>
          </div>

          <form onSubmit={handleSubmit} className="test-form">
            <div className="question-item">
              <div className="question-header">
                <h3>Pitanje {currentQuestionIndex + 1}</h3>
                <span
                  className={`difficulty-badge diff-${
                    currentQuestion.lessonTerm?.term?.difficulty || 'unknown'
                  }`}
                >
                  Težina:{' '}
                  {currentQuestion.lessonTerm?.term?.difficulty || 'Nepoznato'}
                </span>
              </div>

              <div className="question-text">
                {currentQuestion.lang === 'HR' ? (
                  <p>{currentQuestion.questionCro}</p>
                ) : (
                  <p className="jpn-text">{currentQuestion.questionJpn}</p>
                )}
              </div>

              <input
                type="text"
                value={answers[currentQuestion.id]}
                onChange={(e) =>
                  handleAnswerChange(currentQuestion.id, e.target.value)
                }
                placeholder="Upiši svoj odgovor..."
                className="answer-input"
                disabled={submitted}
              />

              {currentQuestion.id in results && (
                <div className="result-indicator">
                  {results[currentQuestion.id] ? (
                    <span className="correct">✓ Točno!</span>
                  ) : (
                    <>
                      <span className="incorrect">✗ Netočno</span>
                      {showAnswers && (
                        <div className="correct-answer">
                          Točan odgovor:{' '}
                          {currentQuestion.lang === 'HR'
                            ? currentQuestion.lessonTerm.term.termNameJpn
                            : currentQuestion.lessonTerm.term.termNameCro}
                        </div>
                      )}
                    </>
                  )}
                </div>
              )}

              {!showHints[currentQuestion.id] ? (
                <button
                  type="button"
                  className="hint-btn"
                  onClick={() => handleHintClick(currentQuestion.id)}
                  disabled={submitted}
                >
                  Prikaži hint
                </button>
              ) : (
                <div className="question-hints">
                  {currentQuestion.lang === 'HR' ? (
                    <span>Hint: {currentQuestion.hintCro}</span>
                  ) : (
                    <span className="jpn-text">
                      ヒント: {currentQuestion.hintJpn}
                    </span>
                  )}
                </div>
              )}
            </div>

            {!submitted ? (
              <button type="submit" className="submit-test-btn">
                Završi
              </button>
            ) : (
              <>
                <button
                  type="button"
                  className="submit-test-btn"
                  onClick={handleRetakeTest}
                >
                  Pokušaj ponovno
                </button>
              </>
            )}
          </form>
        </>
      )}
    </div>
  );
}
