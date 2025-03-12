import './App.css'
import { BrowserRouter as Router } from 'react-router-dom'
import { getApps } from './utils/helper'

/**
 * Root application component
 * Sets up routing and determines which app version to render
 */
function App() {
  // Get appropriate app version based on configuration
  const CurrentApp = getApps();

  return (
    <Router>
      <CurrentApp />
    </Router>
  )
}

export default App
