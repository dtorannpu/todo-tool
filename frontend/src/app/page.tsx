export default function Home() {
  return (
    <a /* eslint-disable-line @next/next/no-html-link-for-pages */
      href={`/auth/login?audience=${process.env.AUTH0_AUDIENCE}`}
      className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-sm"
    >
      ログイン
    </a>
  );
}
