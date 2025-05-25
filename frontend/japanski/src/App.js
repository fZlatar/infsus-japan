import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import MainPage from "./pages/MainPage";
import LoginPage from "./pages/LoginPage";
import LessonsPage from "./pages/LessonsPage";
import TranslatorPage from "./pages/TranslatorPage";
import SettingsPage from "./pages/SettingsPage";
import StandaloneTestPage from "./pages/StandaloneTestPage";
import LessonTestPage from "./pages/LessonTestPage";
import LessonDetailPage from "./pages/LessonDetailPage";
import Sidebar from "./components/Sidebar";
import api from "./api/api";
import './styles/theme.css';


function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
  const email = localStorage.getItem("userEmail");
    if (!email) {
      setIsLoggedIn(false);
    }else {
      setIsLoggedIn(true);
    }
}, []);

  if (isLoggedIn === null) return <div>Provjera prijave...</div>;


  const handleLogout = async () => {
    try {
      await api.post("/auth/logout");
    } catch (err) {
      // Opcionalno: možeš prikazati poruku o grešci
    } finally {
      setIsLoggedIn(false);
      localStorage.removeItem("userEmail");
    }
  };

  return (
    <Router>
      <Sidebar isLoggedIn={isLoggedIn} onLogout={handleLogout} />
      <div style={{ marginLeft: "60px" }}>
        {/* Kad je sidebar otvoren, možeš povećati marginLeft na 220px */}
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/lekcije" element={<LessonsPage />} />
          <Route path="/prevoditelj" element={<TranslatorPage />} />
          <Route path="/postavke" element={<SettingsPage />} />
          <Route path="/login" element={<LoginPage setIsLoggedIn={setIsLoggedIn}/>} />
          <Route path="/lekcije/:lessonId" element={<LessonDetailPage />} />
          <Route path="/lekcije/:lessonId/test" element={<LessonTestPage />} />
          <Route path="/test" element={<StandaloneTestPage />} />
        </Routes>
      </div>
    </Router>
    
  );
}

export default App;
