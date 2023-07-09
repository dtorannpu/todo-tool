import Link from "next/link";

interface TodoItem {
    id: number,
    title: string,
    description: string
}

async function getTodo(id:number) {
    const res = await fetch(`http://localhost:8080/todos/${id}`, { cache: 'no-store' });

    if (!res.ok) {
        throw new Error('データが取得できませんでした。');
    }

    return res.json();
}

export default async function Page({ params }: { params: { id: number } }) {
    const todo = await getTodo(params.id);
    return (
        <div>
            <div>
                <label>タイトル</label>
                {todo.title}
            </div>
            <div>
                <label>内容</label>
                {todo.description}
            </div>
            <Link href="/todo" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">戻る</Link>
            <button className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded">削除</button>
        </div>
    );
}