import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import "../styles/Sidebar.css";

export default function Sidebar({ isLoggedIn, onLogout }) {
  const [open, setOpen] = useState(false);

  const navigate = useNavigate();
  const handleLogoutClick = async () => {
    await onLogout();
    setOpen(false);
    navigate("/"); 
  };

  return (
    <div className={open ? "sidebar sidebar-open" : "sidebar"}>
      <div className="sidebar-toggle" onClick={() => setOpen(!open)}>
        <span className="hamburger">&#9776;</span>
      </div>
      <nav className="sidebar-nav">
        <Link to="/" onClick={() => setOpen(false)}>Početna</Link>
        <Link to="/lekcije" onClick={() => setOpen(false)}>Moje Lekcije</Link>
        <Link to="/prevoditelj" onClick={() => setOpen(false)}>Prevoditelj</Link>
        <Link to="/postavke" onClick={() => setOpen(false)}>Postavke</Link>
        {isLoggedIn ? (
          <button className="logout-btn" onClick={handleLogoutClick}>Odjava</button>
        ) : (
          <>
            <Link to="/login" onClick={() => setOpen(false)}>Prijava</Link>
          </>
        )}
      </nav>
    </div>
  );
}
