import os
import pty
import subprocess
import sys
import select

def autoplay():
    #cmd = ["/usr/lib/jvm/zulu-8/bin/java", "Main"]
    cmd = ["java", "Main"]
    
    # Create a pseudo-terminal
    master_fd, slave_fd = pty.openpty()
    
    # Start the Java process
    proc = subprocess.Popen(
        cmd,
        stdin=slave_fd,
        stdout=slave_fd,
        stderr=slave_fd,
        preexec_fn=os.setsid,
        close_fds=True
    )
    
    os.close(slave_fd)
    
    buffer = ""
    
    try:
        while True:
            # Short timeout for responsiveness
            r, w, e = select.select([master_fd], [], [], 0.01)
            
            if master_fd in r:
                try:
                    data = os.read(master_fd, 8192).decode('utf-8', errors='ignore')
                    if not data:
                        break
                    
                    # Print everything to stdout immediately
                    sys.stdout.write(data)
                    sys.stdout.flush()
                    
                    buffer += data
                    
                    # Process prompts as fast as possible
                    while True:
                        found_prompt = False
                        
                        if "Press ENTER to roll the dice..." in buffer:
                            os.write(master_fd, b"\n")
                            buffer = buffer.replace("Press ENTER to roll the dice...", "", 1)
                            found_prompt = True
                        
                        elif "Enter choice (1 or 2):" in buffer:
                            os.write(master_fd, b"1\n")
                            buffer = buffer.replace("Enter choice (1 or 2):", "", 1)
                            found_prompt = True
                            
                        elif "Enter choice (1 to" in buffer:
                            idx = buffer.find("Enter choice (1 to")
                            colon_idx = buffer.find(":", idx)
                            if colon_idx != -1:
                                os.write(master_fd, b"1\n")
                                # Remove the prompt from buffer so we don't respond to it again
                                buffer = buffer[:idx] + buffer[colon_idx+1:]
                                found_prompt = True
                        
                        elif " (y/n):" in buffer:
                            os.write(master_fd, b"y\n")
                            buffer = buffer.replace(" (y/n):", "", 1)
                            found_prompt = True
                            
                        if not found_prompt:
                            break
                            
                except OSError:
                    break
            
            if proc.poll() is not None:
                break
                
    finally:
        if master_fd:
            try:
                os.close(master_fd)
            except:
                pass
        if proc.poll() is None:
            proc.terminate()

if __name__ == "__main__":
    autoplay()
