import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

/**
 * Vite configuration file
 * Defines build and development settings
 */
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173
  }
})
