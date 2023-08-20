'use client';

import { SubmitHandler, useForm } from 'react-hook-form';

interface IFormInput {
    title: string;
    description: string
}

const TodoForm = () => {
    //const router = useRouter();
    const { reset, register, handleSubmit, formState: { errors, isValid } } = useForm<IFormInput>();
    const onSubmit: SubmitHandler<IFormInput> = async (data: IFormInput) => submit(data);

    async function submit(data: IFormInput) {
        const res = await fetch('http://localhost:3000/api/todos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (!res.ok) {
            throw new Error('登録に失敗しました。');
        }

        reset();
        window.location.href = '/todo'
    }

    return (
        <div className='w-full max-w-xl'>
            <form onSubmit={handleSubmit(onSubmit)} className='bg-white shadow-md px-8 pt-6 pb-8 mb-4'>
                <div className='mb-4'>
                    <label htmlFor="title" className='block text-gray-700 text-sm font-bold mb-2'>タイトル:</label>
                    <input data-testid="titleField" {...register("title", { required: true, maxLength: 128 })} className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight" />
                    {errors?.title?.type === 'required' && <p className='text-red-600'>必須です。</p>}
                    {errors?.title?.type === 'maxLength' && <p className='text-red-600'>タイトルは128文字以内で入力してください。</p>}
                </div>
                <div className='mb-4'>
                    <label htmlFor="description" className='block text-gray-700 text-sm font-bold mb-2'>内容:</label>
                    <textarea data-testid="descriptionField" {...register("description", { required: true, maxLength: 1024 })} className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight"></textarea>
                    {errors?.description?.type === 'required' && <p className='text-red-600'>必須です。</p>}
                    {errors?.description?.type === 'maxLength' && <p className='text-red-600'>内容は1024文字以内で入力してください。</p>}
                </div>
                <div className='flex items-center justify-between'>
                    <input type="submit" value="登録" data-testid="registerButton" disabled={!isValid} className="bg-blue-500 disabled:bg-blue-300 hover:enabled:bg-blue-700 text-white font-bold py-2 px-4 rounded" />
                </div>
            </form>
        </div>
    );
}

export default TodoForm;