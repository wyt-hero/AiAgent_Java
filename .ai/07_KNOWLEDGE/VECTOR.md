# Vector Database Knowledge Base

## Purpose
Reference for vector store integration.

## Stores: pgvector (integrated), Milvus (large-scale), Chroma (dev)

## Embeddings: text-embedding-3-small (1536d), nomic-embed-text (768d), bge-m3 (1024d)

## Metrics: Cosine (default), Euclidean, Dot Product

## Indexing: HNSW (m=16, ef_construction=64)

## Best Practices
- Normalize vectors
- Batch embedding generation
- Monitor recall rate