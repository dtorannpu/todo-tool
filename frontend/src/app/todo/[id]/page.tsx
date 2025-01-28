"use client";

import Link from "next/link";
import { useEffect, use } from "react";
import { SubmitHandler, useForm } from "react-hook-form";

interface IFormInput {
  title: string;
  description: string;
}

export default function Page(props: { params: Promise<{ id: number }> }) {
  const params = use(props.params);
  const {
    register,
    handleSubmit,
    formState: { errors, isValid },
    reset,
  } = useForm<IFormInput>();

  async function getTodo(id: number) {
    const res = await fetch(`http://localhost:3000/api/todos/${id}`, {
      cache: "no-store",
    });

    if (!res.ok) {
      throw new Error("データが取得できませんでした。");
    }

    return res.json();
  }

  useEffect(() => {
    getTodo(params.id).then((data) => {
      reset({
        title: data.title,
        description: data.description,
      });
    });
  }, [params.id, reset]);

  const onSubmit: SubmitHandler<IFormInput> = async (data: IFormInput) =>
    submit(data, params.id);

  async function submit(data: IFormInput, id: number) {
    const res = await fetch(`http://localhost:3000/api/todos/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (!res.ok) {
      throw new Error("更新に失敗しました。");
    }
    window.location.href = "/todo";
  }

  async function deleteTodo(id: number | null) {
    const res = await fetch(`http://localhost:3000/api/todos/${id}`, {
      method: "DELETE",
    });

    if (!res.ok) {
      throw new Error("データが削除できませんでした。");
    }
    window.location.href = "/todo";
  }

  return (
    <div>
      <div className="w-full max-w-xl">
        <form
          onSubmit={handleSubmit(onSubmit)}
          className="bg-white shadow-md px-8 pt-6 pb-8 mb-4"
        >
          <div className="mb-4">
            <label
              htmlFor="title"
              className="block text-gray-700 text-sm font-bold mb-2"
            >
              タイトル:
            </label>
            <input
              {...register("title", { required: true, maxLength: 128 })}
              className="shadow-sm appearance-none border rounded-sm w-full py-2 px-3 text-gray-700 leading-tight"
            />
            {errors?.title?.type === "required" && (
              <p className="text-red-600">必須です。</p>
            )}
            {errors?.title?.type === "maxLength" && (
              <p className="text-red-600">
                タイトルは128文字以内で入力してください。
              </p>
            )}
          </div>
          <div className="mb-4">
            <label
              htmlFor="description"
              className="block text-gray-700 text-sm font-bold mb-2"
            >
              内容:
            </label>
            <textarea
              {...register("description", { required: true, maxLength: 1024 })}
              className="shadow-sm appearance-none border rounded-sm w-full py-2 px-3 text-gray-700 leading-tight"
            ></textarea>
            {errors?.description?.type === "required" && (
              <p className="text-red-600">必須です。</p>
            )}
            {errors?.description?.type === "maxLength" && (
              <p className="text-red-600">
                内容は1024文字以内で入力してください。
              </p>
            )}
          </div>
          <div className="flex items-center justify-between">
            <input
              type="submit"
              value="更新"
              disabled={!isValid}
              className="bg-blue-500 disabled:bg-blue-300 hover:enabled:bg-blue-700 text-white font-bold py-2 px-4 rounded-sm"
            />
          </div>
        </form>
      </div>
      <Link
        href="/todo"
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-sm"
      >
        戻る
      </Link>
      <button
        className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-sm"
        onClick={() => deleteTodo(params.id)}
      >
        削除
      </button>
    </div>
  );
}
