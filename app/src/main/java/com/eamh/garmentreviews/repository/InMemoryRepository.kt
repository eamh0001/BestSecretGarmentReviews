package com.eamh.garmentreviews.repository

import com.eamh.garmentreviews.model.Review
import com.eamh.garmentreviews.model.ReviewType
import com.eamh.garmentreviews.model.Reviewer
import com.eamh.garmentreviews.model.ReviewerType
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class InMemoryRepository: Repository {

    private val reviews: Map<Int,Review> = createMockReviews()

    override fun getReviews(): Single<List<Review>> =
            Single.just(reviews.values.toList()).delay(2, TimeUnit.SECONDS)

    override fun getReview(id: Int): Single<Review> =
            reviews[id]?.
                    let { Single.just(it).delay(2, TimeUnit.SECONDS) }
                    ?: Single.error(Throwable("Review doesn't found"))

    private fun createMockReviews(): Map<Int,Review> =
            mutableMapOf<Int,Review>().apply {
                for(i in 1..100){
                    put(i, createMockReview(i))
                }
            }

    private fun createMockReview(id: Int): Review {
        val type = ReviewType.values()[Random.nextInt(ReviewType.values().size)]
        return Review(
                id = id,
                type = type,
                name = "Name $id Cretan from Minos' city of Knossos",
                designer = "Designer $id Servant of the Delphian Apollo" +
                        " washed in the Castallian Spring",
                description = "Description $id Turdidae species spread the seeds of plants, contributing to the dispersal of many species and the recovery of ecosystems.\n" +
                        "\n" +
                        "Plants have limited seed dispersal mobility away from the parent plant and consequently rely upon a variety of dispersal vectors to transport their propagules, including both abiotic and biotic vectors. Seeds can be dispersed away from the parent plant individually or collectively, as well as dispersed in both space and time.\n" +
                        "\n" +
                        "Many bats and birds rely heavily on fruits for their diet, including birds in the families Cotingidae, Columbidae, Trogonidae, Turdidae, and Ramphastidae. While eating fruit, these animals swallow seeds and then later regurgitate them or pass them in their faeces. Such ornithochory has been a major mechanism of seed dispersal across ocean barriers.\n" +
                        "\n" +
                        "Other seeds may stick to the feet or feathers of birds, and in this way may travel long distances. Seeds of grasses, spores of algae, and the eggs of molluscs and other invertebrates commonly establish in remote areas after long journeys of this sort. The Turdidae have a great ecological importance because some populations migrate long distances and disperse the seeds of endangered plant species at new sites, helping to eliminate inbreeding and increasing the genetic diversity of local flora.",
                price = Random.nextDouble(10.0, 200.0),
                picture = computePathFromType(type),
                date = createMockDate(),
                reviewers = createMockReviewers ()
        )
    }

    private fun createMockDate(): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, - Random.nextInt(12))
        return cal.time // New date
    }

    private fun computePathFromType(type: ReviewType): String {
        return when(type){
            ReviewType.JEAN -> "file:///android_asset/images/jeans.jpg"
            ReviewType.SHIRT -> "file:///android_asset/images/shirt.jpg"
            ReviewType.SHOES -> "file:///android_asset/images/shoes.jpg"
            ReviewType.BLAZER -> "file:///android_asset/images/blazer.jpg"
        }
    }

    private fun createMockReviewers(): List<Reviewer> =
            mutableListOf<Reviewer>().apply{
                for (i in 1..6){
                    add(Reviewer(
                            type = ReviewerType.values()[Random.nextInt(ReviewerType.values().size)],
                            name = "Reviewer $i Cretan from Minos' city of Knossos"
                    ))
                }
            }
}