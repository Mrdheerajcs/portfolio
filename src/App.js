import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Work from "./pages/Work";
import MyWorkRoot from "./pages/MyWorkRoot";


const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/work" element={<Work />} />
        <Route path="/workroot" element={<MyWorkRoot />} />
      </Routes>
    </Router>
  );
};

export default App;
