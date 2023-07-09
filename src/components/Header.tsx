'use client';

import { useUser } from "@auth0/nextjs-auth0/client";
import Link from "next/link";

const Header = () => {
    const { user, isLoading } = useUser();
    return (
        <header>
            <nav>
                <ul>
                    <li>
                        <Link href="/todo">TODO</Link>
                    </li>
                    {!isLoading && !user && (
                        <li>
                            <a href="/api/auth/login">ログイン</a>
                        </li>
                    )}
                    {!isLoading && user && (
                        <li>
                            <a href="/api/auth/logout">ログアウト</a>
                        </li>
                    )}
                </ul>
            </nav>
        </header>
    );
};

export default Header;
