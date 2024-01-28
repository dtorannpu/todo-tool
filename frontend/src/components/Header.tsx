'use client';

import { useUser } from "@auth0/nextjs-auth0/client";
import Link from "next/link";

const Header = () => {
    const { user, isLoading } = useUser();
    return (
        <header>
            <nav className="flex items-center justify-between flex-wrap bg-teal-500 p-6">
                <div className="w-full block flex-grow lg:items-center lg:w-auto">
                    <div className="text-sm lg:flex-grow">
                        <Link href="/todo" className="block mt-4 lg:inline-block lg:mt-0 text-teal-200 hover:text-white mr-4">TODO</Link>

                        {!isLoading && !user && (

                            <a href="/api/auth/login" className="block mt-4 lg:inline-block lg:mt-0 text-teal-200 hover:text-white mr-4">ログイン</a>
                        )}
                        {!isLoading && user && (
                            <a href="/api/auth/logout" className="block mt-4 lg:inline-block lg:mt-0 text-teal-200 hover:text-white mr-4">ログアウト</a>
                        )}
                    </div>
                </div>
            </nav>
        </header>
    );
};

export default Header;
