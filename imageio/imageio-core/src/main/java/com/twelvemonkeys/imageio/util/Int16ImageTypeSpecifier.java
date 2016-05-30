/*
 * Copyright (c) 2014, Harald Kuhr
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name "TwelveMonkeys" nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.twelvemonkeys.imageio.util;

import com.twelvemonkeys.imageio.color.Int16ComponentColorModel;

import javax.imageio.ImageTypeSpecifier;
import java.awt.color.ColorSpace;
import java.awt.image.DataBuffer;
import java.awt.image.PixelInterleavedSampleModel;

/**
 * ImageTypeSpecifier for interleaved 16 bit signed integral samples.
 *
 * @see com.twelvemonkeys.imageio.color.Int16ColorModel
 * @author <a href="mailto:harald.kuhr@gmail.com">Harald Kuhr</a>
 * @author last modified by $Author: haraldk$
 * @version $Id: Int16ImageTypeSpecifier.java,v 1.0 24.01.11 17.51 haraldk Exp$
 */
final class Int16ImageTypeSpecifier extends ImageTypeSpecifier {
    Int16ImageTypeSpecifier(final ColorSpace cs, int[] bandOffsets, final boolean hasAlpha, final boolean isAlphaPremultiplied) {
        super(
                new Int16ComponentColorModel(cs, hasAlpha, isAlphaPremultiplied),
                new PixelInterleavedSampleModel(
                        DataBuffer.TYPE_SHORT, 1, 1,
                        cs.getNumComponents() + (hasAlpha ? 1 : 0),
                        cs.getNumComponents() + (hasAlpha ? 1 : 0),
                        bandOffsets
                )
        );
    }
}
