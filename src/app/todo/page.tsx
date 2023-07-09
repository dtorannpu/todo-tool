
import { withPageAuthRequired } from '@auth0/nextjs-auth0';

export default withPageAuthRequired(async function Page() {
    return (
        <div>
            <h1 className="text-3xl">Todo</h1>
        </div>
    );
});
