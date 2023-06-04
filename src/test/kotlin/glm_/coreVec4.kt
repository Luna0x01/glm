package glm_

import glm_.vec1.Vec1
import glm_.vec1.Vec1d
import glm_.vec2.Vec2
import glm_.vec2.Vec2i
import glm_.vec3.Vec3
import glm_.vec3.Vec3i
import glm_.vec4.*
import glm_.vec4.operators.div
import glm_.vec4.operators.minus
import glm_.vec4.operators.plus
import glm_.vec4.operators.times
import glm_.vec4.swizzle.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kool.BYTES

class coreVec4 : StringSpec() {

    init {

        "ctor"        {

            run {
                val A = Vec4i(1, 2, 3, 4)
                val B = Vec4i(A)
                A shouldBe B
            }

//            #	if GLM_HAS_TRIVIAL_QUERIES
//            //	Error += std::is_trivially_default_constructible<glm::vec4>::value ? 0 : 1;
//            //	Error += std::is_trivially_copy_assignable<glm::vec4>::value ? 0 : 1;
//            Error += std::is_trivially_copyable<glm::vec4>::value ? 0 : 1;
//            Error += std::is_trivially_copyable<glm::dvec4>::value ? 0 : 1;
//            Error += std::is_trivially_copyable<glm::ivec4>::value ? 0 : 1;
//            Error += std::is_trivially_copyable<glm::uvec4>::value ? 0 : 1;
//
//            Error += std::is_copy_constructible<glm::vec4>::value ? 0 : 1;
//            #	endif
//
//            #if GLM_HAS_INITIALIZER_LISTS
//            {
//                glm::vec4 a{ 0, 1, 2, 3 };
//                std::vector<glm::vec4> v = {
//                    {0, 1, 2, 3},
//                    {4, 5, 6, 7},
//                    {8, 9, 0, 1}};
//            }
//
//            {
//                glm::dvec4 a{ 0, 1, 2, 3 };
//                std::vector<glm::dvec4> v = {
//                    {0, 1, 2, 3},
//                    {4, 5, 6, 7},
//                    {8, 9, 0, 1}};
//            }
//            #endif

            run {
                val A = Vec4(1f, 2f, 3f, 4f)
                val B = A.xyzw
                val C = Vec4(A.xyzw)
                val D = Vec4(A.xyzw)
                val E = Vec4(A.x, A.yzw)
                val F = Vec4(A.x, A.yzw)
                val G = Vec4(A.xyz, A.w)
                val H = Vec4(A.xyz, A.w)
                val I = Vec4(A.xy, A.zw)
                val J = Vec4(A.xy, A.zw)
                val K = Vec4(A.x, A.y, A.zw)
                val L = Vec4(A.x, A.yz, A.w)
                val M = Vec4(A.xy, A.z, A.w)

                A shouldBe B
                A shouldBe C
                A shouldBe D
                A shouldBe E
                A shouldBe F
                A shouldBe G
                A shouldBe H
                A shouldBe I
                A shouldBe J
                A shouldBe K
                A shouldBe L
                A shouldBe M
            }
//            #endif// GLM_HAS_UNRESTRICTED_UNIONS && defined(GLM_FORCE_SWIZZLE)

//            #	if GLM_HAS_CONSTEXPR && GLM_ARCH == GLM_ARCH_PURE && !(GLM_COMPILER & GLM_COMPILER_VC) // Visual Studio bug?
//            {
//                constexpr glm::ivec4 v(1);
//
//                Error += v.x == 1 ? 0 : 1;
//                Error += v.y == 1 ? 0 : 1;
//                Error += v.z == 1 ? 0 : 1;
//                Error += v.w == 1 ? 0 : 1;
//            }
//            #	endif
//
            run {
                val A = Vec4(1)
                val B = Vec4(1, 1, 1, 1)

                A shouldEqual B
            }
//
            run {
                arrayOf(
                        Vec4i(Vec2i(1, 2), 3, 4),
                        Vec4i(1, Vec2i(2, 3), 4),
                        Vec4i(1, 2, Vec2i(3, 4)),
                        Vec4i(Vec3i(1, 2, 3), 4),
                        Vec4i(1, Vec3i(2, 3, 4)),
                        Vec4i(Vec2i(1, 2), Vec2i(3, 4)),
                        Vec4i(1, 2, 3, 4),
                        Vec4i(Vec4i(1, 2, 3, 4)))
                        .forEach {
                            it shouldBe Vec4i(1, 2, 3, 4)
                        }
            }

            run {
                val R = Vec1(1f)
                val S = Vec1(2f)
                val T = Vec1(3f)
                val U = Vec1(4.0f)
                val O = Vec4(1f, 2f, 3f, 4f)

                val A = Vec4(R)
                val B = Vec4(1f)
                A shouldEqual B

                val C = Vec4(R, S, T, U)
                C shouldEqual O

                val D = Vec4(R, 2f, 3f, 4f)
                D shouldEqual O

                val E = Vec4(1f, S, 3f, 4f)
                E shouldEqual O

                val F = Vec4(R, S, 3f, 4f)
                F shouldEqual O

                val G = Vec4(1f, 2f, T, 4f)
                G shouldEqual O

                val H = Vec4(R, 2f, T, 4f)
                H shouldEqual O

                val I = Vec4(1f, S, T, 4f)
                I shouldEqual O

                val J = Vec4(R, S, T, 4f)
                J shouldEqual O

                val K = Vec4(R, 2f, 3f, U)
                K shouldEqual O

                val L = Vec4(1f, S, 3f, U)
                L shouldEqual O

                val M = Vec4(R, S, 3f, U)
                M shouldEqual O

                val N = Vec4(1f, 2f, T, U)
                N shouldEqual O

                val P = Vec4(R, 2f, T, U)
                P shouldEqual O

                val Q = Vec4(1f, S, T, U)
                Q shouldEqual O

                val V = Vec4(R, S, T, U)
                V shouldEqual O
            }

            run {
                val R = Vec1(1f)
                val S = Vec1d(2.0)
                val T = Vec1(3.0)
                val U = Vec1d(4.0)
                val O = Vec4(1f, 2.0, 3f, 4.0)

                val A = Vec4(R)
                val B = Vec4(1.0)
                A shouldEqual B

                val C = Vec4(R, S, T, U)
                C shouldEqual O

                val D = Vec4(R, 2f, 3.0, 4f)
                D shouldEqual O

                val E = Vec4(1.0, S, 3f, 4.0)
                E shouldEqual O

                val F = Vec4(R, S, 3.0, 4f)
                F shouldEqual O

                val G = Vec4(1f, 2.0, T, 4.0)
                G shouldEqual O

                val H = Vec4(R, 2.0, T, 4.0)
                H shouldEqual O

                val I = Vec4(1.0, S, T, 4f)
                I shouldEqual O

                val J = Vec4(R, S, T, 4f)
                J shouldEqual O

                val K = Vec4(R, 2f, 3.0, U)
                K shouldEqual O

                val L = Vec4(1f, S, 3.0, U)
                L shouldEqual O

                val M = Vec4(R, S, 3.0, U)
                M shouldEqual O

                val N = Vec4(1f, 2.0, T, U)
                N shouldEqual O

                val P = Vec4(R, 2.0, T, U)
                P shouldEqual O

                val Q = Vec4(1f, S, T, U)
                Q shouldEqual O

                val V = Vec4(R, S, T, U)
                V shouldEqual O
            }

            run {
                val v1_0 = Vec1(1f)
                val v1_1 = Vec1(2f)
                val v1_2 = Vec1(3f)
                val v1_3 = Vec1(4f)

                val v2_0 = Vec2(1f, 2f)
                val v2_1 = Vec2(2f, 3f)
                val v2_2 = Vec2(3f, 4f)

                val v3_0 = Vec3(1f, 2f, 3f)
                val v3_1 = Vec3(2f, 3f, 4f)

                val O = Vec4(1f, 2.0, 3f, 4.0)

                val A = Vec4(v1_0, v1_1, v2_2)
                A shouldEqual O

                val B = Vec4(1f, 2f, v2_2)
                B shouldEqual O

                val C = Vec4(v1_0, 2f, v2_2)
                C shouldEqual O

                val D = Vec4(1f, v1_1, v2_2)
                D shouldEqual O

                val E = Vec4(v2_0, v1_2, v1_3)
                E shouldEqual O

                val F = Vec4(v2_0, 3.0, v1_3)
                F shouldEqual O

                val G = Vec4(v2_0, v1_2, 4.0)
                G shouldEqual O

                val H = Vec4(v2_0, 3f, 4.0)
                H shouldEqual O
            }

            run {
                val v1_0 = Vec1(1f)
                val v1_1 = Vec1(2f)
                val v1_2 = Vec1(3f)
                val v1_3 = Vec1(4f)

                val v2 = Vec2(2f, 3f)

                val v3_0 = Vec3(1f, 2f, 3f)
                val v3_1 = Vec3(2f, 3f, 4f)

                val O = Vec4(1f, 2.0, 3f, 4.0)

                val A = Vec4(v1_0, v2, v1_3)
                A shouldEqual O

                val B = Vec4(v1_0, v2, 4.0)
                B shouldEqual O

                val C = Vec4(1.0, v2, v1_3)
                C shouldEqual O

                val D = Vec4(1f, v2, 4.0)
                D shouldEqual O

                val E = Vec4(1.0, v2, 4f)
                E shouldEqual O
            }
        }

        "bvec4_ctor"        {

            val A = Vec4bool(true)
            val B = Vec4bool(true)
            val C = Vec4bool(false)
            val D = A and B
            val E = A and C
            val F = A or C

            D shouldBe Vec4bool(true)
            E shouldBe Vec4bool(false)
            F shouldBe Vec4bool(true)

            val G = A == C
            val H = A != C
            G shouldBe false
            H shouldBe true
        }

        "vec4 operators" {

            run {
                val A = Vec4i(1)
                val B = Vec4i(1)
                val R = A != B
                val S = A == B

                (S && !R) shouldBe true
            }

            run {
                val A = Vec4(1f, 2f, 3f, 4f)
                val B = Vec4(4f, 5f, 6f, 7f)

                val C = A + B
                C shouldEqual Vec4(5, 7, 9, 11)

                val D = B - A
                D shouldEqual Vec4(3, 3, 3, 3)

                val E = A * B
                E shouldEqual Vec4(4, 10, 18, 28)

                val F = B / A
                F shouldEqual Vec4(4, 2.5, 2, 7f / 4f)

                val G = A + 1f
                G shouldEqual Vec4(2, 3, 4, 5)

                val H = B - 1f
                H shouldEqual Vec4(3, 4, 5, 6)

                val I = A * 2f
                I shouldEqual Vec4(2, 4, 6, 8)

                val J = B / 2f
                J shouldEqual Vec4(2, 2.5, 3, 3.5)

                val K = 1f + A
                K shouldEqual Vec4(2, 3, 4, 5)

                val L = 1f - B
                L shouldEqual Vec4(-3, -4, -5, -6)

                val M = 2f * A
                M shouldEqual Vec4(2, 4, 6, 8)

                val N = 2f / B
                N shouldEqual Vec4(0.5, 2.0 / 5.0, 2.0 / 6.0, 2.0 / 7.0)
            }

            run {
                val A = Vec4i(1f, 2f, 3f, 4f)
                val B = Vec4i(4f, 5f, 6f, 7f)

                A += B
                A shouldBe Vec4i(5, 7, 9, 11)

                A += 1f
                A shouldBe Vec4i(6, 8, 10, 12)
            }

            run {
                val A = Vec4i(1f, 2f, 3f, 4f)
                val B = Vec4i(4f, 5f, 6f, 7f)

                B -= A
                B shouldBe Vec4i(3, 3, 3, 3)

                B -= 1f
                B shouldBe Vec4i(2, 2, 2, 2)
            }
            run {
                val A = Vec4i(1f, 2f, 3f, 4f)
                val B = Vec4i(4f, 5f, 6f, 7f)

                A *= B
                A shouldBe Vec4i(4, 10, 18, 28)

                A *= 2f
                A shouldBe Vec4i(8, 20, 36, 56)
            }
            run {
                val A = Vec4i(1f, 2f, 2f, 4f)
                val B = Vec4i(4f, 4f, 8f, 8f)

                B /= A
                B shouldBe Vec4i(4, 2, 4, 2)

                B /= 2f
                B shouldBe Vec4i(2, 1, 2, 1)
            }
            run {
                val B = Vec4i(2)

                B /= B.y
                B shouldBe Vec4i(1)
            }

            run {
                val A = Vec4i(1f, 2f, 3f, 4f)
                val B = -A
                B shouldBe Vec4i(-1f, -2f, -3f, -4f)
            }

            run {
                var A = Vec4i(1f, 2f, 3f, 4f)
                val B = --A
                B shouldBe Vec4i(0f, 1f, 2f, 3f)
            }

            run {
                var A = Vec4i(1f, 2f, 3f, 4f)
                val B = A--
                B shouldBe Vec4i(1f, 2f, 3f, 4f)
                A shouldBe Vec4i(0f, 1f, 2f, 3f)
            }

            run {
                var A = Vec4i(1f, 2f, 3f, 4f)
                val B = ++A
                B shouldBe Vec4i(2f, 3f, 4f, 5f)
            }

            run {
                var A = Vec4i(1f, 2f, 3f, 4f)
                val B = A++
                B shouldBe Vec4i(1f, 2f, 3f, 4f)
                A shouldBe Vec4i(2f, 3f, 4f, 5f)
            }
        }

        "vec4 equal" {

            run {
                val A = Vec4ui(1, 2, 3, 4)
                val B = Vec4ui(1, 2, 3, 4)
                (A == B) shouldBe true
                (A != B) shouldBe false
            }

            run {
                val A = Vec4i(1, 2, 3, 4)
                val B = Vec4i(1, 2, 3, 4)
                (A == B) shouldBe true
                (A != B) shouldBe false
            }
        }

        "vec4 size" {

            Vec4.size shouldBe 4 * Float.BYTES
            Vec4d.size shouldBe 4 * Double.BYTES
            Vec4.length shouldBe 4
            Vec4d.length shouldBe 4
        }

        "swizzle partial" {

            val A = Vec4(1, 2, 3, 4)

//            #	if GLM_SWIZZLE == GLM_SWIZZLE_OPERATOR
            A shouldEqual Vec4(A.xy, A.zw)

            A shouldEqual Vec4(A.xy, 3f, 4f)

            A shouldEqual Vec4(1f, A.yz, 4f)

            A shouldEqual Vec4(1f, 2f, A.zw)

            A shouldEqual Vec4(A.xyz, 4f)

            A shouldEqual Vec4(1.0f, A.yzw)
//            #	endif//GLM_SWIZZLE == GLM_SWIZZLE_OPERATOR || GLM_SWIZZLE == GLM_SWIZZLE_FUNCTION
        }

        "swizzle" {

            //            #	if GLM_SWIZZLE == GLM_SWIZZLE_OPERATOR
            run {
                val A = Vec4i(1f, 2f, 3f, 4f)
                val B = Vec4i(A.xyzw)
                val C = Vec4i(A.xyzw)
                val D = Vec4i(A.xyzw)
                val E = Vec4i(A.x, A.yzw)
                val F = Vec4i(A.x, A.yzw)
                val G = Vec4i(A.xyz, A.w)
                val H = Vec4i(A.xyz, A.w)
                val I = Vec4i(A.xy, A.zw)
                val J = Vec4i(A.xy, A.zw)
                val K = Vec4i(A.x, A.y, A.zw)
                val L = Vec4i(A.x, A.yz, A.w)
                val M = Vec4i(A.xy, A.z, A.w)

                A shouldBe B
                A shouldBe C
                A shouldBe D
                A shouldBe E
                A shouldBe F
                A shouldBe G
                A shouldBe H
                A shouldBe I
                A shouldBe J
                A shouldBe K
                A shouldBe L
                A shouldBe M
            }
//            #	endif//GLM_SWIZZLE == GLM_SWIZZLE_OPERATOR

//            #	if GLM_SWIZZLE == GLM_SWIZZLE_OPERATOR || GLM_SWIZZLE == GLM_SWIZZLE_FUNCTION
            run {
                val A = Vec4(1f, 2f, 3f, 4f)
                val B = A.xyzw
                val C = Vec4(A.xyzw)
                val D = Vec4(A.xyzw)
                val E = Vec4(A.x, A.yzw)
                val F = Vec4(A.x, A.yzw)
                val G = Vec4(A.xyz, A.w)
                val H = Vec4(A.xyz, A.w)
                val I = Vec4(A.xy, A.zw)
                val J = Vec4(A.xy, A.zw)
                val K = Vec4(A.x, A.y, A.zw)
                val L = Vec4(A.x, A.yz, A.w)
                val M = Vec4(A.xy, A.z, A.w)

                A shouldEqual B
                A shouldEqual C
                A shouldEqual D
                A shouldEqual E
                A shouldEqual F
                A shouldEqual G
                A shouldEqual H
                A shouldEqual I
                A shouldEqual J
                A shouldEqual K
                A shouldEqual L
                A shouldEqual M
            }
//            #	endif//GLM_SWIZZLE == GLM_SWIZZLE_OPERATOR || GLM_SWIZZLE == GLM_SWIZZLE_FUNCTION
        }

        "operator increment"        {

            val v0 = Vec4i(1)
            var v1 = Vec4i(v0)
            var v2 = Vec4i(v0)
            val v3 = ++v1
            val v4 = v2++

            v0 shouldBe v4
            v1 shouldBe v2
            v1 shouldBe v3

            val i0 = 1
            var i1 = i0
            var i2 = i0
            val i3 = ++i1
            val i4 = i2++

            i0 shouldBe i4
            i1 shouldBe i2
            i1 shouldBe i3
        }

//        struct AoS
//                {
//                    glm::vec4 A;
//                    glm::vec3 B;
//                    glm::vec3 C;
//                    glm::vec2 D;
//                };
//
//        static int test_vec4_perf_AoS(std::size_t Size)
//        {
//            int Error(0);
//
//            std::vector<AoS> In;
//            std::vector<AoS> Out;
//            In.resize(Size);
//            Out.resize(Size);
//
//            std::clock_t StartTime = std::clock();
//
//            for(std::size_t i = 0; i < In.size(); ++i)
//            Out[i] = In[i];
//
//            std::clock_t EndTime = std::clock();
//
//            std::printf("AoS: %d\n", static_cast<int>(EndTime - StartTime));
//
//            return Error;
//        }
//
//        static int test_vec4_perf_SoA(std::size_t Size)
//        {
//            int Error(0);
//
//            std::vector<glm::vec4> InA;
//            std::vector<glm::vec3> InB;
//            std::vector<glm::vec3> InC;
//            std::vector<glm::vec2> InD;
//            std::vector<glm::vec4> OutA;
//            std::vector<glm::vec3> OutB;
//            std::vector<glm::vec3> OutC;
//            std::vector<glm::vec2> OutD;
//
//            InA.resize(Size);
//            InB.resize(Size);
//            InC.resize(Size);
//            InD.resize(Size);
//            OutA.resize(Size);
//            OutB.resize(Size);
//            OutC.resize(Size);
//            OutD.resize(Size);
//
//            std::clock_t StartTime = std::clock();
//
//            for(std::size_t i = 0; i < InA.size(); ++i)
//            {
//                OutA[i] = InA[i];
//                OutB[i] = InB[i];
//                OutC[i] = InC[i];
//                OutD[i] = InD[i];
//            }
//
//            std::clock_t EndTime = std::clock();
//
//            std::printf("SoA: %d\n", static_cast<int>(EndTime - StartTime));
//
//            return Error;
//        }
//
//        namespace heap
//                {
//                    struct A
//                            {
//                                float f;
//                            };
//
//                    struct B : public A
//                    {
//                        float g;
//                        glm::vec4 v;
//                    };
//
//                    static int test()
//                    {
//                        int Error = 0;
//
//                        A* p = new B;
//                        p->f = 0.0f;
//                        delete p;
//
//                        Error += sizeof(B) == sizeof(glm::vec4) + sizeof(float) * 2 ? 0 : 1;
//
//                        return Error;
//                    }
//                }//namespace heap
//
//        static int test_vec4_simd()
//        {
//            int Error = 0;
//
//            glm::vec4 const a(std::clock(), std::clock(), std::clock(), std::clock());
//            glm::vec4 const b(std::clock(), std::clock(), std::clock(), std::clock());
//
//            glm::vec4 const c(b * a);
//            glm::vec4 const d(a + c);
//
//            Error += glm::all(glm::greaterThanEqual(d, glm::vec4(0))) ? 0 : 1;
//
//            return Error;
//        }
//
//        static int test_inheritance()
//        {
//            struct my_vec4 : public glm::vec4
//            {
//                my_vec4()
//                : glm::vec4(76.f, 75.f, 74.f, 73.f)
//                , member(82)
//            {}
//
//                int member;
//            };
//
//            int Error = 0;
//
//            my_vec4 v;
//
//            Error += v.member == 82 ? 0 : 1;
//            Error += glm::epsilonEqual(v.x, 76.f, glm::epsilon<float>()) ? 0 : 1;
//            Error += glm::epsilonEqual(v.y, 75.f, glm::epsilon<float>()) ? 0 : 1;
//            Error += glm::epsilonEqual(v.z, 74.f, glm::epsilon<float>()) ? 0 : 1;
//            Error += glm::epsilonEqual(v.w, 73.f, glm::epsilon<float>()) ? 0 : 1;
//
//            return Error;
//        }

        "toColor" {

            Vec4 { it.f }.toColor(normalized = false)
            Vec4(0f, 0.5f, 1f, 1f).toColor()
            Vec4(1f).toColor()

            val c = floatArrayOf(0.2f, 0.2f, 0.2f, 1f)
            Vec4(c, 0).toColor()
        }
    }
}
