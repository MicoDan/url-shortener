import { useNavigate } from "react-router-dom";
import React from "react";
import { motion } from "framer-motion";

import Card from "./Card";
import { useStoreContext } from "../contextApi/ContextApi";

let desc =
  "Easily shorten and share your URLs with Linkly. Track and manage your links in one place, optimizing your digital presence effortlessly. Linkly makes the process of URL shortening quick and simple, empowering users to share links seamlessly across all platforms.";

const LandingPage = () => {
  const navigate = useNavigate();
  const { token } = useStoreContext();
  console.log("TOKEN FROM LANDING PAGE: " + token);

  const dashBoardNavigateHandler = () => {
    navigate("/dashboard");
  };

  return (
    <div className="min-h-[calc(100vh-64px)] lg:px-14 sm:px-8 px-4 bg-white">
      <div className="lg:flex-row flex-col lg:py-5 pt-16 lg:gap-10 gap-8 flex justify-between items-center">
        <div className="flex-1">
          <motion.h1
            initial={{ opacity: 0, y: -80 }}
            whileInView={{
              opacity: 1,
              y: 0,
            }}
            viewport={{ once: true }}
            transition={{ duration: 0.8 }}
            className="font-bold font-roboto text-[#242130] md:text-5xl sm:text-4xl text-3xl md:leading-[55px] sm:leading-[45px] leading-10 lg:w-full md:w-[70%] w-full"
          >
            Streamline Your URL Management with Linkly.
          </motion.h1>
          <p className="text-[#242130] text-sm my-5">
            With Linkly, you can easily create short, memorable URLs for
            faster sharing. Our platform offers advanced tracking tools, giving
            you complete control over your links. Whether you're a business or
            an individual, Linkly helps you optimize your digital presence.
          </p>
          <div className="flex items-center gap-3">
            <motion.button
              initial={{ opacity: 0, y: 80 }}
              whileInView={{
                opacity: 1,
                y: 0,
              }}
              viewport={{ once: true }}
              transition={{ duration: 0.8 }}
              onClick={dashBoardNavigateHandler}
              className="bg-[#242130] w-40 text-white rounded-md py-2"
            >
              Links Dashboard
            </motion.button>
            <motion.button
              initial={{ opacity: 0, y: 80 }}
              whileInView={{
                opacity: 1,
                y: 0,
              }}
              viewport={{ once: true }}
              transition={{ duration: 0.8 }}
              onClick={dashBoardNavigateHandler}
              className="border-[#242130] border w-40 text-[#242130] rounded-md py-2 "
            >
              Create a Short Link
            </motion.button>
          </div>
        </div>
        <div className="flex-1 flex justify-center w-full">
          <motion.img
            initial={{ opacity: 0 }}
            whileInView={{
              opacity: 1,
            }}
            viewport={{ once: true }}
            transition={{ duration: 0.8 }}
            className="sm:w-[480px] w-[400px] object-cover rounded-md"
            src="/images/img2.png"
            alt=""
          />
        </div>
      </div>
      <div className="sm:pt-12 pt-7">
        <motion.p
          initial={{ opacity: 0, y: 50 }}
          whileInView={{
            opacity: 1,
            y: 0,
          }}
          viewport={{ once: true }}
          transition={{ duration: 0.8 }}
          className="text-[#242130] font-roboto font-bold lg:w-[60%] md:w-[70%] sm:w-[80%] mx-auto text-3xl text-center"
        >
          Trusted by innovators and teams worldwide.
        </motion.p>
        <div className="pt-4 pb-7 grid lg:gap-7 gap-4 xl:grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 grid-cols-1 mt-4">
          <Card
            title="Effortless URL Shortening"
            desc="Create short, easy-to-share URLs in just a few clicks. Our simple and efficient process allows you to generate and share links with no hassle."
          />
          <Card
            title="In-Depth Analytics"
            desc="Monitor and analyze the performance of your links with our robust analytics dashboard. Track key metrics like clicks, location, and traffic sources."
          />
          <Card
            title="Top-Notch Security"
            desc="Your privacy and security matter. Our encryption ensures all shortened URLs are safe, giving you peace of mind while sharing your content."
          />
        </div>
      </div>
    </div>
  );
};

export default LandingPage;
