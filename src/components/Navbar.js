import { Link } from "react-scroll";

const Navbar = () => {
  return (
    <>
      <nav className="bg-gray-900 text-white py-4 px-8 fixed w-full z-10 shadow-lg">
        <div className="container mx-auto flex justify-between items-center">
          <h1 className="text-2xl font-bold">Welcome</h1>
          <ul className="hidden md:flex space-x-6">
            <li>
              <Link
                to="home"
                smooth={true}
                duration={500}
                className="hover:text-teal-400 cursor-pointer"
              >
                Home
              </Link>
            </li>
            <li>
              <Link
                to="about"
                smooth={true}
                duration={500}
                className="hover:text-teal-400 cursor-pointer"
              >
                About
              </Link>
            </li>
            <li>
              <Link
                to="education"
                smooth={true}
                duration={500}
                className="hover:text-teal-400 cursor-pointer"
              >
                Education
              </Link>
            </li>
            <li>
              <Link
                to="projects"
                smooth={true}
                duration={500}
                className="hover:text-teal-400 cursor-pointer"
              >
                Projects
              </Link>
            </li>
            <li>
              <Link
                to="contact"
                smooth={true}
                duration={500}
                className="hover:text-teal-400 cursor-pointer"
              >
                Contact
              </Link>
            </li>
          </ul>
          <button className="md:hidden bg-teal-500 px-4 py-2 rounded">
            Menu
          </button>
        </div>
      </nav>
      {/* Add padding to avoid overflow */}
      <div className="pt-16">
        {/* Main content goes here */}
      </div>
    </>
  );
};

export default Navbar;
