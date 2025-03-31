import { Link, useNavigate } from "react-router-dom";
import { FaGraduationCap, FaUserPlus, FaCircleNotch, FaBitcoin, FaBriefcase, FaProjectDiagram, FaUserTie, FaLink, FaSignOutAlt } from "react-icons/fa";

const Sidebar = ({ isOpen, toggle }) => {
  const navigate = useNavigate();
  const role = localStorage.getItem("role");


  const handleLogOut = () => {
    localStorage.clear();
    navigate('/');
  };

  return (
    <div className={`bg-gray-800 text-white w-64 min-h-screen flex flex-col justify-between p-4 absolute md:relative transition-all duration-300 ${isOpen ? "left-0" : "-left-64"} md:left-0`}>

      {/* Top Section - Navigation */}
      <div>
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-xl font-bold">Dashboard</h2>
          <button className="text-white text-xl md:hidden" onClick={toggle}>✖</button>
        </div>

        <nav>
          <ul className="space-y-2">
            {role === "ADMIN" && (<>
              <li><Link to="/dashboard/create-user" className="flex items-center p-2 hover:bg-gray-700 rounded"><FaUserPlus className="mr-2" /> User Register</Link>
              </li>
              <li><Link to="/dashboard/create-portfolio" className="flex items-center p-2 hover:bg-gray-700 rounded"><FaCircleNotch className="mr-2" /> Create Portfolio</Link></li>
            </>
            )}

            <li><Link to="/dashboard/education" className="flex items-center p-2 hover:bg-gray-700 rounded"><FaGraduationCap className="mr-2" /> Education</Link></li>
            <li><Link to="/dashboard/experience" className="flex items-center p-2 hover:bg-gray-700 rounded"><FaBriefcase className="mr-2" /> Experience</Link></li>
            <li><Link to="/dashboard/projects" className="flex items-center p-2 hover:bg-gray-700 rounded"><FaProjectDiagram className="mr-2" /> Projects</Link></li>
            <li><Link to="/dashboard/skills" className="flex items-center p-2 hover:bg-gray-700 rounded"><FaUserTie className="mr-2" /> Skills</Link></li>
            <li><Link to="/dashboard/social-links" className="flex items-center p-2 hover:bg-gray-700 rounded"><FaLink className="mr-2" /> Social Links</Link></li>
          </ul>
        </nav>
      </div>

      {/* Bottom Section - Logout */}
      <div className="mt-auto">
        <button onClick={handleLogOut} className="w-full flex items-center justify-center p-2 bg-red-600 hover:bg-red-700 rounded text-white">
          <FaSignOutAlt className="mr-2" /> Logout
        </button>
      </div>
    </div>
  );
};

export default Sidebar;
