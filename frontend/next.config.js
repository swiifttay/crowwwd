/** @type {import('next').NextConfig} */
// const nextConfig = {};
// https://i.scdn.co/image/ab6761610000e5eb6a224073987b930f99adc706
// module.exports = nextConfig;
module.exports = {
  images: {
    remotePatterns: [
      {
        protocol: 'https',
        hostname: 'i.scdn.co',
        port: '',
        pathname: '/image/**',
      },
    ],
  },
}