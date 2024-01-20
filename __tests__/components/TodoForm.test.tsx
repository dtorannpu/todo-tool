import '@testing-library/jest-dom/vitest';
import TodoForm from '@/components/TodoForm';
import { render, screen, cleanup } from '@testing-library/react';
import userEvent from '@testing-library/user-event'

describe('登録フォーム', () => {
    afterEach(() => {
        cleanup();
    });

    it('レンダリングされるか', () => {
        render(<TodoForm />);

        expect(screen.getByText('タイトル:')).toBeInTheDocument();
        expect(screen.getByText('内容:')).toBeInTheDocument();
        expect(screen.getByText('登録')).toBeInTheDocument();
    });

    it('未入力の時', () => {
        render(<TodoForm />);

        expect(screen.getByTestId('registerButton')).toBeDisabled();
    });

    it('タイトルのみ入力されている時', async () => {
        render(<TodoForm />);
        
        const user = userEvent.setup();
        await user.type(screen.getByTestId('titleField'), 'タイトル');

        expect(screen.getByTestId('registerButton')).toBeDisabled();
    });


    it('内容のみ入力されている時', async () => {
        render(<TodoForm />);

        const user = userEvent.setup();
        await user.type(screen.getByTestId('descriptionField'), '内容');

        expect(screen.getByTestId('registerButton')).toBeDisabled();
    });

    it('タイトルと内容が入力されている時', async () => {
        render(<TodoForm />);

        const user = userEvent.setup();
        await user.type(screen.getByTestId('titleField'), 'タイトル');
        await user.type(screen.getByTestId('descriptionField'), '内容');

        expect(screen.getByTestId('registerButton')).toBeEnabled();
    });
});