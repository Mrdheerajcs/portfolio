import React from "react";
import Navbar from "../components/Navbar";
import Hero from "../components/Hero";
import About from "../components/About";
import Projects from "../components/projects";
import Contact from "../components/Contact";
import Footer from "../components/Footer";
import Education from "../components/Education";


const Home = () => {
  return (
    <>
      <Navbar />
      <Hero />
      <About />
      <Education />
      <Projects />
      <Contact />
      <Footer />
    </>
  );
};

export default Home;
