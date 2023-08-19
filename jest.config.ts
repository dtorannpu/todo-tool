import type { Config } from "jest";
import nextJest from 'next/jest';

const createJestConfig = nextJest({
    dir: './',
});

const config: Config = {
    testEnvironment: 'jest-environment-jsdom',
    moduleNameMapper: {
        '^jose$': require.resolve('jose'),
        '^@panva/hkdf$': require.resolve('@panva/hkdf'),
    },
    setupFilesAfterEnv: ['./setup.ts']
};

export default createJestConfig(config);
