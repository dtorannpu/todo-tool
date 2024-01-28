import Modal from '@/components/Modal';
import TodoForm from '@/components/TodoForm';
import { getAccessToken } from '@auth0/nextjs-auth0';
import Link from 'next/link';

interface TodoItem {
    id: number,
    title: string,
    description: string
}

async function getTodos() {
    const { accessToken } = await getAccessToken();
    const res = await fetch('http://backend:8080/todos', {
        headers: {
            Authorization: `Bearer ${accessToken}`,
        },
        cache: 'no-store'
    });

    if (!res.ok) {
        throw new Error('データが取得できませんでした。');
    }

    return res.json();
}
export default async function Page() {
    const todos = await getTodos();

    return (
        <div>
            <h1 className="text-3xl">Todo</h1>
            <Modal buttonLabel="Todo追加">
                <TodoForm />
            </Modal>
            <table className="border-collapse table-auto w-full">
                <thead>
                    <tr>
                        <th className='border-b dark:border-slate-600 font-medium p-4 pl-8 pt-0 pb-3 text-slate-400 dark:text-slate-200 text-left'>タイトル</th>
                        <th className='border-b dark:border-slate-600 font-medium p-4 pl-8 pt-0 pb-3 text-slate-400 dark:text-slate-200 text-left'>内容</th>
                    </tr>
                </thead>
                <tbody className='bg-white dark:bg-slate-800'>
                    {todos.map((todo: TodoItem) => {
                        return (
                            <tr key={todo.id}>
                                <td className='border-b border-slate-100 dark:border-slate-700 p-4 pl-8 text-slate-500 dark:text-slate-400'><Link href={`/todo/${encodeURIComponent(todo.id)}`} className="no-underline hover:underline text-cyan-600">{todo.title}</Link></td>
                                <td className='border-b border-slate-100 dark:border-slate-700 p-4 text-slate-500 dark:text-slate-400'>{todo.description}</td>
                            </tr>
                        );
                    })}

                </tbody>
            </table>
        </div>
    );
};
