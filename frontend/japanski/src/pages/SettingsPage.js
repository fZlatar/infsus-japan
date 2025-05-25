import React, { useState, useEffect, useContext } from "react";
import { ThemeContext } from "../context/ThemeContext";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import "../styles/SettingsPage.css";

export default function SettingsPage() {
  const { theme, setTheme } = useContext(ThemeContext);
  const navigate = useNavigate();
  const [form, setForm] = useState({
    name: "",
    surname: "",
    dateOfBirth: "",
    gender: "",
    themeColor: "",
    dateFormat: ""
  });
  const [loading, setLoading] = useState(true);
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    const email = localStorage.getItem("userEmail");
    if (!email) {
      navigate("/login", { replace: true });
      return;
    }
    api.get(`/users/${email}`)
      .then(res => {
        const data = res.data;
        setForm({
          name: data.name || "",
          surname: data.surname || "",
          dateOfBirth: data.dateOfBirth || "",
          gender: data.gender || "",
          themeColor: data.themeColor || "",
          dateFormat: data.dateFormat || ""
        });
        setLoading(false);
      })
      .catch(() => {
        setError("Greška pri dohvaćanju korisnika");
        setLoading(false);
      });
  }, []);

  if (loading) return <div>Učitavanje...</div>;
  if (error) return <div>{error}</div>;

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccess("");
    setError("");
    try {
      const email = localStorage.getItem("userEmail");
      const res = await api.put(`/users/${email}`, form);
      if (res.status === 200) {
        setSuccess("Postavke su uspješno spremljene!");
      } else {
        setError("Greška prilikom spremanja postavki.");
      }
    } catch (err) {
      setError("Greška u mreži ili neispravni podaci.");
    }
  };

  return (
    <div className="settings-container">
      <h1>Postavke profila</h1>
      <form className="settings-form" onSubmit={handleSubmit}>
        <div className="form-row">
          <label>
            Ime:
            <input
              type="text"
              name="name"
              value={form.name}
              onChange={handleChange}
              required
            />
          </label>
          <label>
            Prezime:
            <input
              type="text"
              name="surname"
              value={form.surname}
              onChange={handleChange}
              required
            />
          </label>
        </div>
        <div className="form-row">
          <label>
            Datum rođenja:
            <input
              type="date"
              name="dateOfBirth"
              value={form.dateOfBirth}
              onChange={handleChange}
              required
            />
          </label>
          <label>
            Spol:
            <select
              name="gender"
              value={form.gender}
              onChange={handleChange}
              required
            >
              <option value="M">Muško</option>
              <option value="F">Žensko</option>
              <option value="O">Ostalo</option>
            </select>
          </label>
          <label>
            Tema:
            <select
              name="themeColor"
              value={form.themeColor}
              onChange={e => {
                handleChange(e);
                setTheme(e.target.value === "DARK" ? "dark" : "light");
              }}
            >
              <option value="LIGHT">Svijetla</option>
              <option value="DARK">Tamna</option>
            </select>
          </label>
          <label>
            Format datuma:
            <select
              name="dateFormat"
              value={form.dateFormat}
              onChange={handleChange}
            >
              <option value="YYYY-MM-DD">YYYY-MM-DD</option>
              <option value="DD.MM.YYYY">DD.MM.YYYY</option>
              <option value="MM/DD/YYYY">MM/DD/YYYY</option>
            </select>
          </label>
        </div>
        <button type="submit">Spremi postavke</button>
        {success && <div className="success-msg">{success}</div>}
        {error && <div className="error-msg">{error}</div>}
      </form>

    </div>
  );
}
