import React from 'react'
import ShortenItem from './ShortenItem'

/**
 * Component for displaying a list of shortened URLs
 * Renders individual ShortenItem components for each URL
 */
const ShortenUrlList = ({ data }) => {
  return (
    <div className='my-6 space-y-4'>
        {/* Map through URL data and render individual items */}
        {data.map((item) => (
            <ShortenItem key={item.id} {...item} />
        ))}
    </div>
  )
}

export default ShortenUrlList