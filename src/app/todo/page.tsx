
import TodoForm from '@/components/TodoForm';
import Link from 'next/link';

interface TodoItem {
    id: number,
    title: string,
    description: string
}

async function getTodos() {
    const res = await fetch('http://localhost:8080/todos', { cache: 'no-store' });

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

            <TodoForm />
            <table className="table-auto">
                <thead>
                    <tr>
                        <th>タイトル</th>
                        <th>内容</th>
                    </tr>
                </thead>
                <tbody>
                    {todos.map((todo: TodoItem) => {
                        return (
                            <tr key={todo.id}>
                                <td><Link href={`/todo/${encodeURIComponent(todo.id)}`} className="no-underline hover:underline text-cyan-600">{todo.title}</Link></td>
                                <td>{todo.description}</td>
                            </tr>
                        );
                    })}

                </tbody>
            </table>
        </div>
    );
};
