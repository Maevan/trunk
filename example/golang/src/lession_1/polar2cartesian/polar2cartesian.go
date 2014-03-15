package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
	"runtime"
)

type polar struct {
	radius float64
	f      float64
}

type cartesian struct {
	x float64
	y float64
}

var prompt = "Enter a radius and an angle (in degrees), e.g., 12.5 90 " + "or %s to quit."

const result = "Polar radius=%.02f f=%.02f x=%.02f y=%.02f\n"

func init() {
	if runtime.GOOS == "windows" {
		prompt = fmt.Sprintf(prompt, "Ctrl+Z, Enter")
	} else {
		prompt = fmt.Sprintf(prompt, "Ctrl+D")
	}
}

func main() {
	questions := make(chan polar)
	defer close(questions)

	answers := createSolver(questions)
	defer close(answers)
	interact(questions, answers)
}

func createSolver(questions chan polar) chan cartesian {
	answers := make(chan cartesian)
	go func() {
		for {
			polarCoord := <-questions
			f := polarCoord.f * math.Pi / 180.0
			x := polarCoord.radius * math.Cos(f)
			y := polarCoord.radius * math.Sin(f)

			answers <- cartesian{x, y}
		}
	}()
	return answers
}

func interact(questions chan polar, answers chan cartesian) {
	reader := bufio.NewReader(os.Stdin)
	fmt.Println(prompt)

	for {
		fmt.Println("Radius and angle: ")
		line, err := reader.ReadString('\n')
		if err != nil {
			break
		}

		var radius, f float64

		if _, err := fmt.Sscan(line, "%f %f", &radius, &f); err != nil {
			fmt.Println(os.Stderr, "invalid input")
			continue
		}
		questions <- polar{radius, f}
		coord := <-answers
		fmt.Println(result, radius, f, coord.x, coord.y)
	}

	fmt.Println()
}
