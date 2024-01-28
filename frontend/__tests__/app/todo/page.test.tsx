import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/vitest';
import Page from '@/app/todo/page';
import { GetAccessTokenResult } from '@auth0/nextjs-auth0';

describe('Todo一覧', () => {
    afterEach(() => {
        vi.resetAllMocks();
    });

    it('一覧に取得したデータが表示される。', async () => {

        const spy = vi.spyOn(require('@auth0/nextjs-auth0'), 'getAccessToken').mockImplementation(() => Promise.resolve<GetAccessTokenResult>({ accessToken: 'test' }));
        const fetchMock = vi
            .spyOn(global, 'fetch')
            .mockImplementation(async () => new Response(`[
                    {
                        "id": 1,
                        "title": "テスト1"
                    },
                    {
                        "id": 2,
                        "title": "テスト2"
                    }
                ]`, { status: 200 }));
        const b = render(await Page());

        expect(spy).toHaveBeenCalled();

        expect(screen.getByText('テスト1')).toBeInTheDocument();
        expect(screen.getByText('テスト2')).toBeInTheDocument();

        expect(fetchMock).toHaveBeenCalledTimes(1);
        expect(fetchMock).toHaveBeenCalledWith(
            'http://localhost:8080/todos',
            {
                cache: "no-store",
                headers: {
                    'Authorization': 'Bearer test'
                }
            }
        );
    });

    it('エラー表示', async () => {

        const spy = vi.spyOn(require('@auth0/nextjs-auth0'), 'getAccessToken').mockImplementation(() => Promise.resolve<GetAccessTokenResult>({ accessToken: 'test' }));

        const fetchMock = vi
            .spyOn(global, 'fetch')
            .mockImplementation(async () => new Response(`[
                {
                    id: 1,
                    title: 'テスト1'
                },
                {
                    id: 2,
                    title: 'テスト2'
                }
            ]`, { status: 400 }));
        expect(async () => { render(await Page()); }).rejects.toThrowError(new Error('データが取得できませんでした。'));
    });
});