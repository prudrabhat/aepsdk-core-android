/*
  Copyright 2024 Adobe. All rights reserved.
  This file is licensed to you under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software distributed under
  the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
  OF ANY KIND, either express or implied. See the License for the specific language
  governing permissions and limitations under the License.
 */
package com.adobe.marketing.mobile.core.testapp.ui.inappmessage

import com.adobe.marketing.mobile.core.testapp.permissionDialog
import com.adobe.marketing.mobile.services.Log
import com.adobe.marketing.mobile.services.ServiceProvider
import com.adobe.marketing.mobile.services.ui.InAppMessage
import com.adobe.marketing.mobile.services.ui.Presentable
import com.adobe.marketing.mobile.services.ui.PresentationError
import com.adobe.marketing.mobile.services.ui.message.InAppMessageEventListener
import com.adobe.marketing.mobile.services.ui.message.InAppMessageSettings
import com.adobe.marketing.mobile.util.DefaultPresentationUtilityProvider

object InAppMessageCreator {
    private const val LOG_TAG = "InAppMessageCreator"

    private const val sampleHTML = "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "  <script type=\"text/javascript\">\n" +
            "            function callNative(action) {\n" +
            "                try {\n" +
            "                    // the name of the message handler is the same name that must be registered in native code.\n" +
            "                    // in this case the message name is \"Android\"\n" +
            "                    Android.run(action);\n" +
            "                } catch(err) {\n" +
            "                    console.log(err); }\n" +
            "                }\n" +
            "            </script>" +
            "    <title>Responsive Webpage</title>\n" +
            "</head>\n" +
            "<body style=\"margin: 0; font-family: Arial, sans-serif; background-color: black; color: white;\">\n" +
            "\n" +
            "    <header style=\"background-color: #333; text-align: center; padding: 1rem;\">\n" +
            "        <h1>Fictional Webpage</h1>\n" +
            "    </header>\n" +
            "\n" +
            "    <main style=\"text-align: center;\">\n" +
            "        <img src=\"https://picsum.photos/id/234/100/100\" alt=\"Sample Image\" style=\"max-width: 100%; height: auto; padding: 1rem; display: block; margin: 0 auto;\">\n" +
            "        <button onclick=\"callNative('native callbacks are cool!')\">Native callback!</button>" +
            "    </main>\n" +
            "\n" +
            "\n" +
            "</body>\n" +
            "</html>"

    private val anotherHtml = """
        <html><head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta charset="UTF-8">
                <style>
                  body {
                    margin: 0;
                    padding: 0;
                    user-select: none;
                  }

                  .slider {
                    position: absolute;
                    top: 0;
                    left: 0;
                    bottom: 3.125rem;
                    right: 0;
                    z-index: 100;
                  }
                </style>
              </head>
              <body>
                <div style="box-sizing:border-box;height:100vh;width:100%;flex-direction:column;position:relative;display:flex;user-select:none;justify-content:flex-end;overflow:hidden" id="root"><div style="box-sizing:border-box;width:100%;display:flex;flex-direction:column;justify-content:flex-end;align-items:center;gap:1.5rem;padding-bottom:2.5rem" id="staticContainer"><div id="buttonGroup-group" style="z-index:200;width:100%"><div style="box-sizing:border-box;width:100%;display:flex;flex-direction:row;justify-content:center;align-items:center;gap:1rem;padding-left:1.5rem;padding-right:1.5rem;border-width:0" id="buttonGroup"><a id="continue" style="display:flex;align-items:center;min-width:100px;height:2.8125rem;width:100%;justify-content:center;user-select:none"><div style="display:flex;align-items:center;justify-content:center;flex-grow:0;height:3rem;box-sizing:border-box;background-color:#2548F5;font-size:1rem;padding-bottom:0.75rem;padding-left:2rem;padding-right:2rem;padding-top:0.75rem;width:auto;border-radius:1.40625rem"><div id="continue" contenteditable="false" onclick= enableNotifications() style="white-space:pre-wrap;font-family:Poppins;line-height:150%;width:auto;outline:none;box-sizing:border-box;position:relative;text-align:center;color:#FFFFFF">Enable Notifications</div></div></a><a id="finish" style="display: none; align-items: center; min-width: 100px; height: 2.8125rem; width: 100%; justify-content: center; user-select: none;"><div style="display:flex;align-items:center;justify-content:center;flex-grow:0;height:3rem;box-sizing:border-box;background-color:#2548F5;font-size:1rem;padding-bottom:0.75rem;padding-left:1rem;padding-right:1rem;padding-top:0.75rem;width:auto;border-radius:1.40625rem"><div id="finish" onclick= enableNotifications(); contenteditable="false" style="white-space:pre-wrap;font-family:Poppins;line-height:150%;width:auto;outline:none;box-sizing:border-box;position:relative;text-align:center;color:#FFFFFF">Let's start</div></div></a></div></div><div id="carousel" style="display:flex;align-items:center;justify-content:center;gap:0.625rem;margin:1rem 0;z-index:1"><div id="carousel-0" style="width: 20px; height: 0.625rem; border-radius: 0.3125rem; background-color: rgb(0, 0, 0); opacity: 1;"></div></div></div><div style="box-sizing:border-box;height:100%;width:100%;position:absolute;top:0;right:0;display:flex;flex-direction:column;user-select:none;justify-content:flex-start;padding:1rem" id="page0"><div id="background" style="position:absolute;top:0;left:0;width:100%;height:100%;z-index:-1;background-color:#FFFFFF"></div><div style="box-sizing:border-box;display:flex;flex-direction:column;justify-content:flex-start;align-items:center;gap:1rem;top:0;bottom:0;left:0;right:0;position:absolute" id="content"><div id="imageContainer" style="display:flex;overflow:hidden;position:relative;width:auto;border-end-start-radius:3rem;border-end-end-radius:3rem"><img id="image" style="width:100%;aspect-ratio:1/1;object-fit:cover;object-position:center" src="https://cdn.experience-stage.adobe.net/solutions/dms-mobile-taurus/assets/ai-page1.0c1f5cbf.jpeg"></div><div style="box-sizing:border-box;width:100%;display:flex;flex-direction:column;justify-content:flex-start;align-items:center;gap:1rem;padding-left:1rem;padding-right:1rem" id="textContainer"><div id="title" contenteditable="false" style="white-space:pre-wrap;font-family:Noka;line-height:150%;width:100%;outline:none;box-sizing:border-box;position:relative;text-align:center;color:#000000;font-size:2rem">Stay Updated!</div><div id="body" contenteditable="false" style="white-space:pre-wrap;font-family:Poppins;line-height:150%;width:100%;outline:none;box-sizing:border-box;position:relative;color:#242424;font-size:1rem;text-align:center">Stay updated with real-time offers and news. Enable notifications for timely alerts and exclusive deals.</div></div></div></div></div>
                <div id="slider" class="slider" style="cursor: grab;"></div>
                <script>
                  
            var pageCount = 1;
            let log = (event) => { console.log(event) };
            let onClose = () => { console.log('closing') };
            let startViewDoc = performance.now();
            

          const animations = [];
          const sceneWatchers = [];
          let currentScene = 0;
          let isAnimating = false;
          let manualPosition;
          let manualScene;
          let startViewScene = performance.now();

          registerAnimation = (animation) => {
            animations.push(animation);
          }

          registerSceneWatcher = (watcher) => {
            sceneWatchers.push(watcher);
          }

          function animateToPosition(now, sceneToRender) {
            animations.forEach(animation => {
              const { duration, easing, delay = 0, draw, scene = 1 } = animation;
              if (scene !== sceneToRender) return;
              let timeFraction = 0;

              if (now > (delay || 0)) {
                // timeFraction = (time - (start + delay)) / duration;
                timeFraction = (now - delay) / duration;
                if (timeFraction > 1) { timeFraction = 1; }
              }

              // calculate the current animation state
              const progress = easing ? easing(timeFraction) : timeFraction;
              //console.log(">", timeFraction, progress);
              draw(progress); // draw it
            });
          }

          let sceneLengths = [];

          function calculateSceneLength(scene) {
            if (sceneLengths[scene]) { return sceneLengths[scene]; }
            const sceneLength = animations.reduce((acc, animation) => {
              if ((animation.scene || 1) !== scene) return acc;
              return Math.max(acc, (animation.delay || 0) + (animation.duration || 0));
            }, 0);
            sceneLengths[scene] = sceneLength;
            return sceneLength;
          }
          
        function enableNotifications() {
          try {
            console.log('Enabling Notifications');
            Android.run("enableNotifications()");
          } catch (e) {
            console.log(e);
          }
          return "hi";
        }

          function setCurrentScene(scene, source) {
            const time = performance.now();
            const duration = time - startViewScene;
            startViewScene = time;

            let oldScene = currentScene;
            currentScene = scene;
            sceneWatchers.forEach(watcher => watcher(scene, oldScene, { source, duration }));
          }

          function playAnimations(scene, source, reverse = false, offset = 0) {
            let start = performance.now();
            isAnimating = true;
            setCurrentScene(scene, source);

            // determine scene length
            const sceneLength = calculateSceneLength(scene);
            
            requestAnimationFrame(function animate(time) {
              const offsetTime = time + offset;
              const now = reverse ? sceneLength - (offsetTime - start) : offsetTime - start;
              
              animateToPosition(now, currentScene);

              const moreTime = reverse ? now > 0 : now < sceneLength;

              if (moreTime) {
                requestAnimationFrame(animate);
              } else {
                isAnimating = false;
                if (reverse) { 
                  setCurrentScene(currentScene - 1, source);
                }
              }
          
            });
          }

            
          const slider = document.querySelector('.slider')
          let isPressed = false;
          let isMoving = false;
          let cursorX;

          
          const onMouseDown = (e) => {
            isPressed = true;
            cursorX = e.offsetX || e?.touches[0]?.clientX || 0;
            slider.style.cursor = "grabbing";
          };

          const onMouseUp = () => {
            slider.style.cursor = "grab";
            isPressed = false;
            if (!isMoving) return;
            isMoving = false;

            const windowWidth = window.innerWidth;
            if (manualScene === 0 || manualScene >= pageCount) { return; }

            if (manualPosition < windowWidth / 8) {
              console.log("Reset back");
            }

            if (manualScene === currentScene && currentScene > 0) {
              playAnimations(currentScene, 'drag', true, manualPosition);
            } else {
              playAnimations(currentScene + 1, 'drag', false, manualPosition);
            }
          };

          const onMouseMove = (e) => {
            if (!isPressed) return;
            e.preventDefault();

            if (isAnimating) return;
            const newX = e.offsetX || e?.touches[0]?.clientX || 0;

            const windowWidth = window.innerWidth;
            const sliderPosition = newX - cursorX;

            if (Math.abs(sliderPosition) < 5) return;
            isMoving = true;

            manualScene = currentScene;
            if (sliderPosition < 0) { manualScene = currentScene + 1; }
            
            const sceneLength = calculateSceneLength(manualScene);
            const changeInterval = (sliderPosition / windowWidth);

            if (sliderPosition >= 0) {
              manualPosition = sceneLength - (changeInterval * sceneLength);
            } else {
              manualPosition = changeInterval * sceneLength * -1;
            }
            
            if (manualScene === 0 || manualScene >= pageCount) { return; }

            requestAnimationFrame(() => {
              animateToPosition(manualPosition, manualScene);
            });
          };

          slider.addEventListener("mousedown", onMouseDown);
          slider.addEventListener("touchstart", onMouseDown);
          
          slider.addEventListener("mouseup", onMouseUp);
          slider.addEventListener("touchend", onMouseUp);
          
          slider.addEventListener("mousemove", onMouseMove);  
          slider.addEventListener("touchmove", onMouseMove);

          // triggers mouseup event if user leaves the window
          document.body.addEventListener("mouseleave", onMouseUp);

            
          translate = ({
            id, 
            startPosition,
            endPosition, 
            toOnScreen = true,
            direction = 'left',
            easing
          }) => (progress) => {
            // validate input
            if (!(startPosition && endPosition) && 
              !(startPosition && direction) &&
              !(endPosition && direction))
            {
              throw new Error('translate called with invalid inputs');
            }

            const elem = document.getElementById(id);
            const isHorizontal = direction === 'left' || direction === 'right';
            const hDirection = isHorizontal && direction === 'left' ? 'right' : 'left';
            const vDirection = !isHorizontal && direction === 'up' ? 'bottom' : 'top';

            let start = startPosition;
            let end = endPosition;

            // translating to on screen
            if (!start) {
              start = {};
              if (isHorizontal) {
                start.x = endPosition?.x - window.innerWidth;
                start.y = endPosition?.y;
              } else {
                start.x = endPosition?.x;
                start.y = endPosition?.y - window.innerHeight;
              }
            }

            // translating to off screen
            if (!end) {
              end = {};
              if (isHorizontal) {
                end.x = startPosition?.x + window.innerWidth;
                end.y = startPosition?.y;
              } else {
                end.x = startPosition?.x;
                end.y = startPosition?.y + window.innerHeight;
              }
            }

            elem.style[hDirection] = (progress * (end.x - start.x) + start.x) + 'px';
            elem.style[vDirection] = (progress * (end.y - start.y) + start.y) + 'px';
          };  

            
          displayHide = ({
            id, 
            type,
            display
          }) => (progress) => {
            if ((progress === 1 && type === 'display') || (progress !== 1 && type === 'hide')) {
              document.getElementById(id).style.display = display;
            } else {
              document.getElementById(id).style.display = 'none';
            }
          };  

            
          function easeInExpo(x) {return x === 0 ? 0 : Math.pow(2, 10 * x - 10)}
          function easeOutExpo(x) {return x === 1 ? 1 : 1 - Math.pow(2, -10 * x)}


            
              registerAnimation({
                scene: 0,
                delay: undefined,
                duration: 500,
                draw: displayHide({ 
                  id: "continue", 
                  type: "hide",
                  display: "flex",
                })
              });
              
              registerAnimation({
                scene: 0,
                delay: undefined,
                duration: 500,
                draw: displayHide({ 
                  id: "finish", 
                  type: "display",
                  display: "flex",
                })
              });
              
            
                registerSceneWatcher((newScene, oldScene) => {
                  // HARD CODED FOR MODERN STYLE FOR NOW

                  // unhighlight the old button
                  if (oldScene !== newScene) {
                    const oldDot = document.getElementById('carousel-' + oldScene);
                    oldDot.style.width = '7px';
                    oldDot.style.opacity = '0.4';
                  }

                  const newDot = document.getElementById('carousel-' + newScene);
                  newDot.style.width = '20px';
                  newDot.style.opacity = '1';
                }) 
              

            // apply all the initial values for each animation
            // some of these values are off screen and depend on screen width
            animateToPosition(0, 0)

            
                    document.getElementById("continue").addEventListener("click", () => {
                      if (isAnimating) return;
                      log({ type: 'buttonPress', action: 'nextPage', elementId: 'continue' });
                      playAnimations(currentScene + 1, 'button');
                    });
                  
                    document.getElementById("finish").addEventListener("click", () => {
                      if (isAnimating) return;
                      log({ type: 'buttonPress', action: 'close', elementId: 'finish' });
                      onClose({ duration: performance.now() - startViewDoc });
                    });
                  
            setCurrentScene(0);
          
          log = (message) => {
            window.parent.postMessage({ type: 'content-log', message });
          };

          registerSceneWatcher((newScene, oldScene, meta) => {
            if (newScene === oldScene) return;
            log({ type: 'changePage', oldPage: oldScene, newPage: newScene, ...meta });
          }) 

          onClose = (meta) => {
            log({ type: 'close', page: currentScene, ...meta });
          };

                </script>
              
              
        </body></html>
    """.trimIndent()

    private val iamSettings = InAppMessageSettings.Builder()
        .content(anotherHtml)
        .height(100)
        .width(100)
        .backgroundColor("#FF0000")
        .cornerRadius(10f)
        .displayAnimation(InAppMessageSettings.MessageAnimation.BOTTOM)
        .dismissAnimation(InAppMessageSettings.MessageAnimation.TOP)
        .gestureMap(
            mapOf(
                "swipeUp" to "https://adobe.com",
                "swipeDown" to "https://adobe.com",
                "swipeLeft" to "https://adobe.com",
                "swipeRight" to "https://google.com",
                "tapBackground" to "https://google.com"
            )
        )

    private val iamEventListener = object : InAppMessageEventListener {
        override fun onBackPressed(message: Presentable<InAppMessage>) {}
        override fun onUrlLoading(message: Presentable<InAppMessage>, url: String): Boolean {
            return false
        }

        override fun onShow(presentable: Presentable<InAppMessage>) {
            val message = presentable.getPresentation()
            message.eventHandler.evaluateJavascript("enableNotifications();"){
                Log.debug("UIServicesView", LOG_TAG, "Javascript evaluation result: $it")
            }
            message.eventHandler.handleJavascriptMessage("Android") {
                Log.debug("UIServicesView", LOG_TAG, "Message from InAppMessage: $it")
                permissionDialog.show()
            }
        }

        override fun onHide(presentable: Presentable<InAppMessage>) {}
        override fun onDismiss(presentable: Presentable<InAppMessage>) {}
        override fun onError(presentable: Presentable<InAppMessage>, error: PresentationError) {}
    }

    fun create(): Presentable<InAppMessage> = ServiceProvider.getInstance().uiService.create(
        InAppMessage(iamSettings.build(), iamEventListener),
        DefaultPresentationUtilityProvider()
    )
}