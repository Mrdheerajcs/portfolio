import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Work from "./pages/Work";
import MyWorkRoot from "./pages/MyWorkRoot";
import Dashboard from "./pages/Dashboard";
import Login from "./pages/Login";
import Education from "./components/masters/Education";
import Experience from "./components/masters/Experience";
import Projects from "./components/masters/Projects";
import Skills from "./components/masters/Skills";
import SocialLinks from "./components/masters/SocialLinks";
import PrivateRoute from "./ApiConfig/PrivateRoute";
import UserPortfolio from "./components/masters/AdminMaster/UserPortfolio";
import UserRegistation from "./components/masters/AdminMaster/UserRegistation";


const protectedRoutes = [
  {
    path: "/dashboard/*",
    element: <Dashboard />,
    allowedRoles: ["USER", "ADMIN"],
    children: [
      { path: "create-user", element: <UserRegistation />, allowedRoles: ["ADMIN"] },
      { path: "create-portfolio", element: <UserPortfolio />, allowedRoles: ["ADMIN"] },
      { path: "education", element: <Education />, allowedRoles: ["USER", "ADMIN"] },
      { path: "experience", element: <Experience />, allowedRoles: ["USER", "ADMIN"] },
      { path: "projects", element: <Projects />, allowedRoles: ["USER", "ADMIN"] },
      { path: "skills", element: <Skills />, allowedRoles: ["USER", "ADMIN"] },
      { path: "social-links", element: <SocialLinks />, allowedRoles: ["USER", "ADMIN"] },
    ],
  },
];



const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/work" element={<Work />} />
        <Route path="/workroot" element={<MyWorkRoot />} />
        <Route path="/login" element={<Login />} />

        {/* Dashboard Layout */}
        {protectedRoutes.map((route) => (
          <Route key={route.path} path={route.path} element={
            <PrivateRoute allowedRoles={route.allowedRoles}>
              {route.element}
            </PrivateRoute>
          }>
            {route.children?.map((child) => (
              <Route
                key={child.path}
                path={child.path}
                element={
                  <PrivateRoute allowedRoles={child.allowedRoles}>
                    {child.element}
                  </PrivateRoute>
                }
              />
            ))}
          </Route>
        ))}
      </Routes>
    </Router>
  );
};

export default App;