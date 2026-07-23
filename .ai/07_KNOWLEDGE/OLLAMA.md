# Ollama Knowledge Base

## Purpose
Reference for Ollama LLM integration.

## API: REST on port 11434 (/api/generate, /api/chat, /api/embeddings)

## Models: qwen3-vl (vision), llama3:8b (text), nomic-embed-text (embeddings)

## Best Practices
- Pull models at startup
- Use streaming for long responses
- Set appropriate timeouts
- Monitor GPU memory