package de.osca.android.district.mobility.data.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import org.locationtech.jts.geom.Coordinate

object CoordinateSerializer : KSerializer<Coordinate> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Coordinate") {
        element<Double>("x")
        element<Double>("y")
    }

    override fun serialize(encoder: Encoder, value: Coordinate) {
        encoder.encodeStructure(descriptor) {
            if (value != null) {
                encodeSerializableElement(descriptor, 0, Double.serializer(), value.x)
                encodeSerializableElement(descriptor, 1, Double.serializer(), value.y)
            }
        }
    }

    override fun deserialize(decoder: Decoder): Coordinate {
        return decoder.decodeStructure(descriptor) {
            var x: Double? = null
            var y: Double? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeSerializableElement(descriptor, 0, Double.serializer())
                    1 -> y = decodeSerializableElement(descriptor, 1, Double.serializer())
                    DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index $index")
                }
            }
            return@decodeStructure Coordinate(
                requireNotNull(x),
                requireNotNull(y),
            )
        }
    }

}
