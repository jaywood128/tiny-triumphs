/** @type {import('ts-jest').JestConfigWithTsJest} */
module.exports = {
  // testEnvironment: 'node',
  // transformIgnorePatterns: ['<rootDir>/node_modules/'],
  // roots: ['<rootDir>/src'],
  // transform: {
  //   "^.+\\.tsx?$": "ts-jest"
  // },
  // testRegex: '(/__tests__/.*|(\\.|/)(test|spec))\\.tsx?$',
  // moduleFileExtensions: [ "ts",
  // "tsx",
  // "js",
  // "jsx",
  // "json",
  // "node"],
  // collectCoverage: true,
  // clearMocks: true,
  // coverageDirectory: "coverage",

  preset: 'ts-jest',
  testEnvironment: 'node',
  testRegex: '(/__tests__/.*|(\\.|/)(test|spec))\\.(jsx?|tsx?)$',
  moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx', 'json', 'node'],
  collectCoverage: true,
  clearMocks: true,
  coverageDirectory: 'coverage',
};