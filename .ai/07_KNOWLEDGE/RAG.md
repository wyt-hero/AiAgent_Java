# RAG Knowledge Base

## Purpose
Reference for Retrieval-Augmented Generation.

## Pipeline: Document → Chunk → Embed → Store → Query → Search → Context → LLM

## Chunking: Fixed-size (512 tokens), Semantic, Recursive

## Retrieval: Cosine similarity, top-K (default 5), relevance threshold (0.7)

## Best Practices
- Test chunk size with real data
- Include metadata in chunks
- Use hybrid search