/**
 * Tailwind CSS configuration
 * Customizes the framework's styling options
 */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      backgroundImage: {
        "custom-gradient": "linear-gradient(to right, #3b82f6, #9333ea)", // equivalent to from-blue-500 to-purple-600
        "custom-gradient-2": "linear-gradient(to left, #3b82f6, #f43f5e)",
        "card-gradient": "linear-gradient(to right, #38b2ac, #4299e1)",
      },
      colors: {
        navbarColor: "#ffffff",
        btnColor: "#3364F7",
        customRed: "#FF0000",
        linkColor: "#0000EE"
      },
      boxShadow: {
        custom: "rgba(0, 0, 0, 0.24) 0px 3px 8px"
      },
      fontFamily: {
        roboto: ["Roboto", "sans-serif"],
        montserrat: ["Montserrat", "sans-serif"],
      },
    },
  },

  variants: {
    extend: {
      backgroundImage: ["responsive"],
    },
  },

  plugins: [],
};