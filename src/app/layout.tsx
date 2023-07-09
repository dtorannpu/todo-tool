import './globals.css'
import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import { UserProvider } from '@auth0/nextjs-auth0/client';
import Header from '@/components/Header';
import { Suspense } from 'react';
import Loading from './loading';


const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'TODOアプリケーション',
  description: 'TODOアプリケーションです。',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="ja">
      <UserProvider>
        <body className={inter.className}>
          <Suspense fallback={<Loading />}>
            <Header />
            {children}
          </Suspense>
        </body>
      </UserProvider>
    </html>
  )
}
