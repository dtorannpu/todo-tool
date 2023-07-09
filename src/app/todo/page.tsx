
import { withPageAuthRequired } from '@auth0/nextjs-auth0';

export default withPageAuthRequired(async function Page() {
    return (
        <div>
            <h1 className="text-3xl">Todo</h1>

            <table className="table-auto">
                <thead>
                    <tr>
                        <th>タイトル</th>
                        <th>内容</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>タイトル</td>
                        <td>内容</td>
                    </tr>
                </tbody>
            </table>
        </div>
    );
});
