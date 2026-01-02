package dev.waylon.player.apis.adapter.transformer

/**
 * Unified Transformer Interface
 * 
 * Defines a common contract for transforming data from one type to another.
 * This interface is used to separate the data transformation logic from the business logic,
 * making the code more modular and easier to maintain.
 *
 * @param I Input type - the type of data to be transformed
 * @param O Output type - the type of data after transformation
 */
interface Transformer<I, O> {
    /**
     * Transforms input data to output data
     *
     * @param input The input data to be transformed
     * @return The transformed output data
     */
    fun transform(input: I): O
}
