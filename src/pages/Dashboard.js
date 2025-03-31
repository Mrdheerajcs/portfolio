import { useState } from "react";
import { Routes, Route } from "react-router-dom";
import Sidebar from "../components/Layout/Sidebar";
import Header from "../components/Layout/Header";
import Footer from "../components/Layout/Footer";
import { Outlet } from "react-router-dom";
const Dashboard = () => {
  const [isOpen, setIsOpen] = useState(false);
  const toggleSidebar = () => setIsOpen(!isOpen);

  return (
    <div className="flex h-screen flex-col md:flex-row">
      <Sidebar isOpen={isOpen} toggle={toggleSidebar} />
      <div className="flex-1 flex flex-col">
        <Header toggle={toggleSidebar} />
        <main className="flex-1 p-5 overflow-auto">
        <Outlet />
        </main>
        <Footer />
      </div>
    </div>
  );
};

export default Dashboard;
