import { Route, Routes } from "react-router-dom";
import Navbar from "./components/NavBar";
import ShortenUrlPage from "./components/ShortenUrlPage";
import { Toaster } from "react-hot-toast";
import Footer from "./components/Footer";
import LandingPage from "./components/LandingPage";
import AboutPage from "./components/AboutPage";
import RegisterPage from "./components/RegisterPage";
import LoginPage from "./components/LoginPage";
import DashboardLayout from "./components/Dashboard/DashboardLayout";
import PrivateRoute from "./PrivateRoute";
import ErrorPage from "./components/ErrorPage";

/**
 * Main router component handling application routing
 * Defines routes and their corresponding components
 */
const AppRouter = () => {
    // Check if current path should hide header/footer
    const hideHeaderFooter = location.pathname.startsWith("/s");

    return (
        <>
            {/* Conditionally render header based on route */}
            {!hideHeaderFooter && <Navbar />}
            <Toaster position='bottom-center'/>
            
            {/* Define application routes */}
            <Routes>
                <Route path="/" element={<LandingPage />} />
                <Route path="/about" element={<AboutPage />} />
                <Route path="/s/:url" element={<ShortenUrlPage />} />
                
                {/* Protected routes with authentication */}
                <Route path="/register" element={
                    <PrivateRoute publicPage={true}>
                        <RegisterPage />
                    </PrivateRoute>
                } />
                <Route path="/login" element={
                    <PrivateRoute publicPage={true}>
                        <LoginPage />
                    </PrivateRoute>
                } />
                <Route path="/dashboard" element={
                    <PrivateRoute publicPage={false}>
                        <DashboardLayout />
                    </PrivateRoute>
                } />
                
                {/* Error handling routes */}
                <Route path="/error" element={<ErrorPage />} />
                <Route path="*" element={
                    <ErrorPage message="We can't seem to find the page you're looking for"/>
                } />
            </Routes>
            
            {/* Conditionally render footer based on route */}
            {!hideHeaderFooter && <Footer />}
        </>
    );
}

/**
 * Router component for subdomain handling
 * Manages URL redirects on custom subdomains
 */
export const SubDomainRouter = () => {
    return (
        <Routes>
            <Route path="/:url" element={<ShortenUrlPage />} />
        </Routes>
    );
}

export default AppRouter;