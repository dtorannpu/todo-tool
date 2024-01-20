import '@testing-library/jest-dom/vitest';
import { render, screen, cleanup } from '@testing-library/react';
import Home from '@/app/page';

describe('Home', () => {
    afterEach(() => {
        cleanup();
    });

    it('ログインボタンが表示される。', () => {
        render(<Home />);
        expect(screen.getByRole('link', { name: 'ログイン' })).toHaveAttribute('href', '/api/auth/login');
    });
});
