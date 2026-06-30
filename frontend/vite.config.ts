import { defineConfig } from 'vitest/config'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  test: {
    environment: 'jsdom',
    setupFiles: './src/test/setup.ts',
    globals: true,
  },
})

/**
 * ESSE BLOCO DIZ:
 * 
 * environment: 'jsdom'
→ os testes terão um ambiente parecido com navegador

setupFiles
→ arquivo executado antes dos testes

globals: true
→ permite usar describe, it e expect sem importar em todo arquivo
 */