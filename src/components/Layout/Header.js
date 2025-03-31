import React from 'react'
import { FaBars } from "react-icons/fa";
const Header = ({ toggle }) => {
  const email = localStorage.getItem('email');
  return (
    <header className="bg-gray-900 text-white p-4 flex justify-between items-center">
    <button className="md:hidden" onClick={toggle}><FaBars size={24}/></button>
    <h1 className="text-lg font-bold">Dashboard</h1>
    <h1>Hi, {email}</h1>
  </header>
  )
}

export default Header