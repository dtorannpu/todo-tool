'use client';

import { SubmitHandler, useForm } from 'react-hook-form';

interface IFormInput {
    title: string;
    description: String
}

async function test(data: IFormInput) {
    await fetch('http://localhost:8080/todos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
}

const TodoForm = () => {
    const { register, handleSubmit, formState: { errors } } = useForm<IFormInput>();
    const onSubmit: SubmitHandler<IFormInput> = async (data) => test(data);

    return (
        <div className='w-full max-w-xl'>
            <form onSubmit={handleSubmit(onSubmit)} className='bg-white shadow-md px-8 pt-6 pb-8 mb-4'>
                <div className='mb-4'>
                    <label htmlFor="title" className='block text-gray-700 text-sm font-bold mb-2'>タイトル:</label>
                    <input {...register("title", { required: true, maxLength: 128 })} className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
                    {errors?.title?.type === 'required' && <p className='text-red-600'>必須です。</p>}
                    {errors?.title?.type === 'maxLength' && <p className='text-red-600'>タイトルは128文字以内で入力してください。</p>}
                </div>
                <div className='mb-4'>
                    <label htmlFor="description" className='block text-gray-700 text-sm font-bold mb-2'>内容:</label>
                    <textarea {...register("description", { required: true, maxLength: 1024 })} className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"></textarea>
                    {errors?.description?.type === 'required' && <p className='text-red-600'>必須です。</p>}
                    {errors?.description?.type === 'maxLength' && <p className='text-red-600'>内容は1024文字以内で入力してください。</p>}
                </div>
                <div className='flex items-center justify-between'>
                    <input type="submit" value="登録" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" />
                </div>
            </form>
        </div>
    );
}

export default TodoForm;