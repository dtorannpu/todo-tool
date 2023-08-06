'use client'

import Link from "next/link";
import { useRouter } from 'next/navigation';

interface TodoItem {
    id: number,
    title: string,
    description: string
}

export default async function Page({ params }: { params: { id: number } }) {
    const router = useRouter();
    const todo = await getTodo(params.id);

    async function getTodo(id: number) {
        const res = await fetch(`http://localhost:3000/api/todos/${id}`, {
            cache: 'no-store'
        });

        if (!res.ok) {
            throw new Error('データが取得できませんでした。');
        }

        return res.json();
    }

    async function deleteTodo(id: number) {
        const res = await fetch(`http://localhost:3000/api/todos/${id}`, {
            method: 'DELETE'
        });

        if (!res.ok) {
            throw new Error('データが削除できませんでした。');
        }
        
        router.push('/todo');
        router.refresh();
    }

    return (
        <div>
            <div>
                <label>タイトル：</label>
                {todo.title}
            </div>
            <div>
                <label>内容：</label>
                {todo.description}
            </div>
            <Link href="/todo" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">戻る</Link>
            <button className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded" onClick={() => deleteTodo(todo.id)}>削除</button>
        </div>
    );
}