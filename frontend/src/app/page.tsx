export default function Home() {
  return (
    <a
      href={`/auth/login?audience=${process.env.NEXT_PUBLIC_AUTH0_AUDIENCE}&returnTo=/todo`}
      className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-sm"
    >
      ログイン
    </a>
  );
}
