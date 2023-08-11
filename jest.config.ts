import type { Config } from 'jest';
import nextJest from 'next/jest';

const createJestConfig = nextJest({
    dir: './'
});

const config: Config = {
    setupFilesAfterEnv: ['<rootDir>/jest-setup.js'],
    moduleDirectories: ['node_modules', '<rootDir>/'],
    testEnvironment: 'jest-environment-jsdom',
    collectCoverageFrom: [
        '<rootDir>/**/*.{js,jsx,ts,tsx}',
        '<rootDir>/src/app/page.tsx',
        '!**/*.d.ts',
        '!**/node_modules/**',
        '!<rootDir>/out/**',
        '!<rootDir>/.next/**',
        '!<rootDir>/*.config.{js,ts}',
        '!<rootDir>/coverage/**',
        '!<rootDir>/jest-setup.ts',
    ]
};

export default createJestConfig(config);
