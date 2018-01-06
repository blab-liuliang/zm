//********************************************************
// Origin Decoder
//********************************************************
JSMpeg.Decoder.MPEG5Video = (function(){ "use strict";

    var MPEG5 = function(options) {
        JSMpeg.Decoder.Base.call(this, options);

        this.width = 0;
        this.height = 0;
        this.cb = 0;
        this.decoder = new libde265.Decoder();
        this.decoder.set_framerate_ratio(30);
    };

    MPEG5.prototype = Object.create(JSMpeg.Decoder.Base.prototype);
    MPEG5.prototype.constructor = MPEG5;

    MPEG5.prototype.write = function(pts, buffers) {

        var isArrayOfBuffers = (typeof(buffers[0]) === 'object');
        if (isArrayOfBuffers) {
            for (var i = 0; i < buffers.length; i++) {
                this.decoder.push_data(buffers[i]);
            }
        }
        else {
            this.decoder.push_data(buffers);
        }
    };

    MPEG5.prototype._set_error = function(error, message) {
        if (this.error_cb) {
            this.error_cb(error, message);
        }
    };

    MPEG5.prototype.decodePicture = function(image) {
        this.currentFrame++;

        var w = image.get_width();
        var h = image.get_height();
        var stride = 0;
        var y = libde265.de265_get_image_plane(image.img, 0, stride);
        var u = libde265.de265_get_image_plane(image.img, 1, stride);
        var v = libde265.de265_get_image_plane(image.img, 2, stride);

        if (this.destination) {
            if (w != this.width || h != this.height) {
                this.width = w;
                this.height = h;
                this.destination.resize(this.width, this.height);
            }
        }

        // Invoke decode callbacks
        if (this.destination) {
            this.destination.render( y, u, v);
        }

        image.free();
    };

    MPEG5.prototype.decode = function() {

        if (this.cb==0){
            this.cb=1;

            var that = this;
            this.decoder.set_image_callback(function(image) {
                that.decodePicture(image);
            });
        }

        this.decoder.decode(function(err) {
            switch(err) {
                case libde265.DE265_ERROR_WAITING_FOR_INPUT_DATA:
                    //setTimeout(decode, 0);
                    return;

                default:
                    if (!libde265.de265_isOK(err)) {
                        this._set_error(err, libde265.de265_get_error_text(err));
                        return;
                    }
            }

            return;
            //decoder.free();
            //that.stop();
        });

        return true;
    };

    return MPEG5;

})();
