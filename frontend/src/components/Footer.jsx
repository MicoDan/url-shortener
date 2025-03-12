import React from "react";
import { FaFacebook, FaTwitter, FaInstagram, FaLinkedin } from "react-icons/fa";

const Footer = () => {
  return (
    <footer className="bg-[#242130] text-white py-8 z-40 relative">
      <div className="container mx-auto px-6 lg:px-14 flex flex-col lg:flex-row lg:justify-between items-center gap-4">
        <div className="text-center lg:text-left">
          <h2 className="text-3xl font-bold mb-2">Linkly</h2>
          <p>Simplifying URL shortening for efficient sharing</p>
        </div>

        <p className="mt-4 lg:mt-0">
          &copy; 2025 Linkly.
        </p>

        <div className="flex space-x-6 mt-4 lg:mt-0">
          <a href="https://www.instagram.com/mico.dan.50/" target="blank" className="hover:text-[#FF6363]">
            <FaInstagram size={24} />
          </a>
          <a href="https://www.linkedin.com/in/mico-dan-778732258/" target="blank" className="hover:text-[#FF6363]">
            <FaLinkedin size={24} />
          </a>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
