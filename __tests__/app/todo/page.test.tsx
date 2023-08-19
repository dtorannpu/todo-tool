import { enableFetchMocks } from 'jest-fetch-mock';
enableFetchMocks();

import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import Page from '@/app/todo/page';
import { GetAccessTokenResult } from '@auth0/nextjs-auth0';
import exp from 'constants';

describe('Todo一覧', () => {
    beforeEach(async () => {
        fetchMock.doMock();
    });

    it('一覧に取得したデータが表示される。', async () => {

        const spy = jest.spyOn(require('@auth0/nextjs-auth0'), 'getAccessToken').mockImplementation(() => Promise.resolve<GetAccessTokenResult>({ accessToken: 'test' }));

        fetchMock.mockResolvedValue({
            ok: true,
            json: async () => ([
                {
                    id: 1,
                    title: 'テスト1'
                },
                {
                    id: 2,
                    title: 'テスト2'
                }
            ])
        } as Response);

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

        const spy = jest.spyOn(require('@auth0/nextjs-auth0'), 'getAccessToken').mockImplementation(() => Promise.resolve<GetAccessTokenResult>({ accessToken: 'test' }));

        fetchMock.mockResolvedValue({
            ok: false,
            json: async () => ([
                {
                    id: 1,
                    title: 'テスト1'
                },
                {
                    id: 2,
                    title: 'テスト2'
                }
            ])
        } as Response);
        //const a = render(await Page());
        expect(async () => {render(await Page());}).rejects.toThrowError(new Error('データが取得できませんでした。'));
    });
});