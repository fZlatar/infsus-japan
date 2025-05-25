import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import "../styles/AuthForm.css";

export default function AuthFlipForm({ setIsLoggedIn }) {
  const [isFlipped, setIsFlipped] = useState(false);

  // Login state
  const [loginForm, setLoginForm] = useState({ email: "", password: "" });
  const [loginError, setLoginError] = useState("");
  const [loginEmailError, setLoginEmailError] = useState("");
  const emailRegex = /^[\-A-Za-z0-9._%+]+@[\-A-Za-z0-9.]+\.[A-Za-z]{2,}$/;

  // Register state
  const [registerForm, setRegisterForm] = useState({
    email: "",
    name: "",
    surname: "",
    dateOfBirth: "",
    gender: "",
    rawPassword: ""
  });
  const [registerError, setRegisterError] = useState("");
  const [registerSuccess, setRegisterSuccess] = useState("");
  const [registerEmailError, setRegisterEmailError] = useState("");

  const navigate = useNavigate();

  // Login handlers
  const handleLoginChange = (e) => {
    const { name, value } = e.target;
    setLoginForm({ ...loginForm, [name]: value });
    if (name === "email") {
      setLoginEmailError(emailRegex.test(value) ? "" : "Neispravan email.");
    }
  };
  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    setLoginError("");
    if (!emailRegex.test(loginForm.email)) {
      setLoginEmailError("Neispravan email.");
      return;
    }
    try {
      const res = await api.post("/auth/login", loginForm);
      if (res.status === 200) {
        localStorage.setItem("userEmail", loginForm.email);
        setIsLoggedIn(true);
        navigate("/lekcije");
      } else {
        setLoginError("Neispravni podaci.");
      }
    } catch {
      setLoginError("Neispravni podaci ili greška u mreži.");
    }
  };

  // Register handlers
  const handleRegisterChange = (e) => {
    const { name, value } = e.target;
    setRegisterForm({ ...registerForm, [name]: value });
    if (name === "email") {
      setRegisterEmailError(emailRegex.test(value) ? "" : "Neispravan email.");
    }
  };
  const handleRegisterSubmit = async (e) => {
    e.preventDefault();
    setRegisterError("");
    setRegisterSuccess("");
    if (!emailRegex.test(registerForm.email)) {
      setRegisterEmailError("Neispravan email.");
      return;
    }
    try {
      const res = await api.post("/auth/register", registerForm);
      if (res.status === 200 || res.status === 201) {
        localStorage.setItem("userEmail", registerForm.email);
        setIsLoggedIn(true);
        navigate("/lekcije");
      } else {
        setRegisterError("Greška prilikom registracije.");
      }
    } catch (err) {
      if (err.response && err.response.status === 409) {
        setRegisterEmailError("Email je već zauzet.");
      } else if (err.response && err.response.data && err.response.data.message) {
        setRegisterError(err.response.data.message);
      } else {
        setRegisterError("Greška u mreži ili neispravni podaci.");
      }
    }
  };

  return (
    <div className={`card auth-flip-card${isFlipped ? " flipped" : ""}`}>
      <div className="card2 auth-flip-card-inner">
        {/* LOGIN */}
        <form className="form auth-flip-front" onSubmit={handleLoginSubmit} style={{display: isFlipped ? "none" : "flex"}}>
          <p id="heading">Prijava</p>
          <div className="field">
            <svg viewBox="0 0 16 16" fill="currentColor" height="16" width="16" className="input-icon">
              <path d="M13.106 7.222c0-2.967-2.249-5.032-5.482-5.032-3.35 0-5.646 2.318-5.646 5.702 0 3.493 2.235 5.708 5.762 5.708.862 0 1.689-.123 2.304-.335v-.862c-.43.199-1.354.328-2.29.328-2.926 0-4.813-1.88-4.813-4.798 0-2.844 1.921-4.881 4.594-4.881 2.735 0 4.608 1.688 4.608 4.156 0 1.682-.554 2.769-1.416 2.769-.492 0-.772-.28-.772-.76V5.206H8.923v.834h-.11c-.266-.595-.881-.964-1.6-.964-1.4 0-2.378 1.162-2.378 2.823 0 1.737.957 2.906 2.379 2.906.8 0 1.415-.39 1.709-1.087h.11c.081.67.703 1.148 1.503 1.148 1.572 0 2.57-1.415 2.57-3.643zm-7.177.704c0-1.197.54-1.907 1.456-1.907.93 0 1.524.738 1.524 1.907S8.308 9.84 7.371 9.84c-.895 0-1.442-.725-1.442-1.914z"></path>
            </svg>
            <input
              type="email"
              name="email"
              className="input-field"
              placeholder="Email"
              autoComplete="off"
              value={loginForm.email}
              onChange={handleLoginChange}
              required
            />
            <div
              className="error-msg"
              style={{
                minHeight: "18px",
                visibility: loginEmailError ? "visible" : "hidden",
                color: "#e53935",
                fontSize: "0.9em",
                marginTop: "2px"
              }}
            >
              {loginEmailError || " "}
            </div>
          </div>
          <div className="field">
            <svg viewBox="0 0 16 16" fill="currentColor" height="16" width="16" className="input-icon">
              <path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z"></path>
            </svg>
            <input
              type="password"
              name="password"
              className="input-field"
              placeholder="Lozinka"
              value={loginForm.password}
              onChange={handleLoginChange}
              required
            />
          </div>
          <div className="btn">
            <button className="button1" type="submit">
              Prijavi se
            </button>
          </div>
          <div style={{textAlign: "center", marginTop: "1.2em"}}>
            <button
              type="button"
              className="button2"
              onClick={() => setIsFlipped(true)}
              style={{background: "none", color: "var(--color-primary)", textDecoration: "underline", fontWeight: 500, border: "none", cursor: "pointer"}}
            >
              Nemaš račun? Registriraj se
            </button>
          </div>
          {loginError && <div className="error-msg">{loginError}</div>}
        </form>

        <form className="form auth-flip-back" onSubmit={handleRegisterSubmit} style={{display: isFlipped ? "flex" : "none"}}>
          <p id="heading">Registracija</p>
          {/* Email */}
          <div className="field">
            <svg viewBox="0 0 16 16" fill="currentColor" className="input-icon">
              <path d="M2 4a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V4zm2-.5a.5.5 0 0 0-.5.5v.217l5.8 3.633a.5.5 0 0 0 .4 0L14.5 4.217V4a.5.5 0 0 0-.5-.5H4zm10 2.383-5.6 3.509a1.5 1.5 0 0 1-1.2 0L2 5.883V12a.5.5 0 0 0 .5.5h11a.5.5 0 0 0 .5-.5V5.883z"/>
            </svg>
            <input
              type="email"
              name="email"
              className="input-field"
              placeholder="Email"
              value={registerForm.email}
              onChange={handleRegisterChange}
              required
            />
            <div
              className="error-msg"
              style={{
                minHeight: "18px", // ili height: "18px" ako želiš fiksno
                visibility: registerEmailError ? "visible" : "hidden",
                color: "#e53935",
                fontSize: "0.9em",
                marginTop: "2px"
              }}
            >
              {registerEmailError || " "}
            </div>
          </div>
          {/* Ime i prezime u jednom redu */}
          <div className="field-row">
            <div className="field half">
              <svg viewBox="0 0 16 16" fill="currentColor" className="input-icon">
                <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm0 1c-2.33 0-7 1.17-7 3.5V15h14v-2.5C15 10.17 10.33 9 8 9z"/>
              </svg>
              <input
                type="text"
                name="name"
                className="input-field"
                placeholder="Ime"
                value={registerForm.name}
                onChange={handleRegisterChange}
                required
              />
            </div>
            <div className="field half">
              <svg viewBox="0 0 16 16" fill="currentColor" className="input-icon">
                <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm0 1c-2.33 0-7 1.17-7 3.5V15h14v-2.5C15 10.17 10.33 9 8 9z"/>
              </svg>
              <input
                type="text"
                name="surname"
                className="input-field"
                placeholder="Prezime"
                value={registerForm.surname}
                onChange={handleRegisterChange}
                required
              />
            </div>
          </div>
          {/* Datum rođenja i spol u jednom redu */}
          <div className="field-row">
            <div className="field two-thirds">
              <svg viewBox="0 0 16 16" fill="currentColor" className="input-icon">
                <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2V.5a.5.5 0 0 1 .5-.5zM2 3v1h12V3a1 1 0 0 0-1-1H3a1 1 0 0 0-1 1zm12 2H2v9a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1V5z"/>
              </svg>
              <input
                type="date"
                name="dateOfBirth"
                className="input-field"
                placeholder="Datum rođenja"
                value={registerForm.dateOfBirth}
                onChange={handleRegisterChange}
                required
              />
            </div>
            <div className="field one-third">
              <svg viewBox="0 0 16 16" fill="currentColor" className="input-icon">
                <circle cx="8" cy="8" r="3"/>
                <path d="M8 1v2M8 13v2M1 8h2M13 8h2"/>
              </svg>
              <select
                name="gender"
                className="input-field"
                value={registerForm.gender}
                onChange={handleRegisterChange}
                required
              >
                <option value="">Spol</option>
                <option value="M">Muško</option>
                <option value="F">Žensko</option>
                <option value="O">Ostalo</option>
              </select>
            </div>
          </div>
          {/* Lozinka */}
          <div className="field">
            <svg viewBox="0 0 16 16" fill="currentColor" className="input-icon">
              <path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z"/>
            </svg>
            <input
              type="password"
              name="rawPassword"
              className="input-field"
              placeholder="Lozinka"
              value={registerForm.rawPassword}
              onChange={handleRegisterChange}
              required
            />
          </div>
          <div className="btn">
            <button className="button1" type="submit">
              Registriraj se
            </button>
          </div>
          <div style={{textAlign: "center", marginTop: "1.2em"}}>
            <button
              type="button"
              className="button2"
              onClick={() => setIsFlipped(false)}
            >
              Već imaš račun? Prijavi se
            </button>
          </div>
          {registerError && <div className="error-msg">{registerError}</div>}
          {registerSuccess && <div className="success-msg">{registerSuccess}</div>}
        </form>
      </div>
    </div>
  );
}